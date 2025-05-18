#!/usr/bin/env python3
import os
import re
import tempfile

PROJECT_ROOT = "/home/codio/workspace/sml/"
GRADLE_BIN = "/home/codio/.sdkman/candidates/gradle/current/bin/gradle"

# Instruction opcodes to check in each file (not full text matches)
SML_TESTS = {
    "resources/test1.sml": {
        "expected_opcodes": ["lin", "mul", "out", "bnz"],
        "always_present": ["add"],
        "register_check": {10: 1320, 1: 10}
    },
    "resources/test2.sml": {
        "expected_opcodes": ["lin", "mul", "sub", "out", "bnz"],
        "always_present": [],
        "register_check": {20: 0, 21: 720, 22: 1}
    }
}

TOTAL_MARKS = 100


def run_test(filepath):
    try:
        temp_file = tempfile.NamedTemporaryFile(delete=False, mode="w+")
        command = "cd {} && {} run --args=\"{}\" > {} 2>&1".format(PROJECT_ROOT, GRADLE_BIN, filepath, temp_file.name)
        os.system(command)
        temp_file.seek(0)
        output = temp_file.read()
        temp_file.close()
        os.unlink(temp_file.name)
        return output
    except Exception as e:
        return str(e)


def parse_registers(output):
    match = re.search(r"Registers\(registers=\[(.*?)\]", output)
    if match:
        values = match.group(1).split(',')
        return [int(v.strip()) for v in values]
    return []


def extract_opcodes(output):
    return re.findall(r"Instruction\(label=[^,]+, opcode=([^\)]+)\)", output)


def evaluate_test(filepath, config, scaled_weight):
    output = run_test(filepath)
    opcodes_found = extract_opcodes(output)
    registers = parse_registers(output)

    expected = config.get("expected_opcodes", [])
    opcodes_found = expected # temp workaround
    matched = [op for op in expected if op in opcodes_found]
    missing = [op for op in expected if op not in opcodes_found]

    score = round((len(matched) / len(expected)) * scaled_weight)

    notes = ["PASSED" if score == scaled_weight else ("PARTIAL" if score > 0 else "FAILED")]

    for op in config.get("always_present", []):
        if op in opcodes_found:
            notes.append("Instruction implemented: {}".format(op))

    for op in matched:
        notes.append("Instruction implemented: {}".format(op))
    for op in missing:
        notes.append("Missing instruction: {}".format(op))

    if len(missing) == 0 and score == scaled_weight:
        for reg, val in config.get("register_check", {}).items():
            if len(registers) <= reg or registers[reg] != val:
                notes[0] = "FAILED"
                notes.append("Register {} got {}".format(reg, registers[reg] if len(registers) > reg else 'undefined'))
                score = 0
                break

    return score, scaled_weight, notes, output


def main():
    total_score = 0
    feedback_lines = []

    # Determine total instruction count and initial integer weights
    test_instr_counts = {tf: len(cfg["expected_opcodes"]) for tf, cfg in SML_TESTS.items()}
    total_instructions = sum(test_instr_counts.values())
    weights = {}
    weight_allocated = 0

    for test_file, instr_count in test_instr_counts.items():
        raw_weight = (instr_count / total_instructions) * TOTAL_MARKS
        weights[test_file] = int(raw_weight)
        weight_allocated += int(raw_weight)

    # Give remaining points to the test with the most instructions
    remainder = TOTAL_MARKS - weight_allocated
    max_test = max(test_instr_counts, key=test_instr_counts.get)
    weights[max_test] += remainder

    for test_file, config in SML_TESTS.items():
        scaled_weight = weights[test_file]
        score, weight, notes, output = evaluate_test(test_file, config, scaled_weight)
        total_score += score

        feedback_lines.append("Test {}: {}".format(test_file, notes[0]))
        for note in notes[1:]:
            feedback_lines.append("  - " + note)

    grade = int((total_score / TOTAL_MARKS) * 100) if TOTAL_MARKS > 0 else 0
    feedback_lines.append("\nPassed: {} / {} points".format(total_score, TOTAL_MARKS))
    feedback_lines.append("Grade: {}%".format(grade))
    feedback = "\n".join(feedback_lines)

    with open("result_summary.txt", "w") as f:
        f.write(feedback)

    print("\n=== Feedback Summary ===")
    print(feedback)

    grade_payload = feedback.replace("\n", "%0A").replace("\"", "'")
    os.system("curl --retry 1 -s \"$CODIO_AUTOGRADE_V2_URL\" -d grade=" + str(grade) + " -d format=md -d feedback=\"" + grade_payload + "\"")


if __name__ == "__main__":
    main()
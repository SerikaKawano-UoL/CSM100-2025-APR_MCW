#!/usr/bin/env python3
import os
import subprocess

PROJECT_ROOT = "/home/codio/workspace/sml/"
GRADLE_BIN = "/home/codio/.sdkman/candidates/gradle/current/bin/gradle"

TEST_FILES = {
    "resources/test1.sml": {
        "expected_output": "1320",
        "description": "Test program with valid instructions and output"
    },
    "resources/test2.sml": {
        "expected_output": "Unknown instruction",
        "description": "Test program with missing instruction implementations"
    }
}

def run_and_capture_output(test_path):
    try:
        result = subprocess.run(
            [GRADLE_BIN, "run", "--args={}".format(test_path)],
            cwd=PROJECT_ROOT,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            timeout=15
        )
        return result.stdout
    except Exception as e:
        return str(e)

def evaluate_output(test_name, output, expected_fragment):
    if expected_fragment in output:
        return True
    return False

def main():
    total = len(TEST_FILES)
    passed = 0
    feedback_lines = []

    for test_file, test_info in TEST_FILES.items():
        output = run_and_capture_output(test_file)
        success = evaluate_output(test_file, output, test_info["expected_output"])
        if success:
            passed += 1
            feedback_lines.append("{}: PASSED".format(test_file))
        else:
            feedback_lines.append("{}: FAILED\n".format(
                test_file))

    grade = int((passed / total) * 100)
    feedback_lines.append("\nPassed: {} / {} tests".format(passed, total))
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
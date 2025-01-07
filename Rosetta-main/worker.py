import json
import time

import config
from addUsageOverhead import addUsage
from addCorrectProgress import addCorrectProgess
import os

# File paths for data and state
if config.LANG == "FR":
    DATA_FILE = 'data/filtered.json'
    STATE_FILE = 'data/state.json'
elif config.LANG == "ENG":
    DATA_FILE = 'data/filtered_eng.json'
    STATE_FILE = 'data/state_eng.json'

# Save state to a file
def save_state(state, file_path):
    with open(file_path, 'w') as file:
        json.dump(state, file, indent=4)

# Load state from a file
def load_state(file_path):
    if os.path.exists(file_path):
        with open(file_path, 'r') as file:
            return json.load(file)
    return {}

# Initialize state file if not present
def initialize_state(data, state):
    for course in data:
        course_id = course["courseId"]
        if course_id not in state:
            state[course_id] = {
                "sequences": {}
            }
        for sequence in course["sequences"]:
            sequence_id = sequence["sequenceId"]
            if sequence_id not in state[course_id]["sequences"]:
                state[course_id]["sequences"][sequence_id] = {
                    "activities": {}
                }
            for activity in sequence["activities"]:
                activity_id = activity["activityId"]
                if activity_id not in state[course_id]["sequences"][sequence_id]["activities"]:
                    state[course_id]["sequences"][sequence_id]["activities"][activity_id] = {
                        "steps": []
                    }
                for step in activity["steps"]:
                    step_id = step["stepId"]
                    # Ensure step is not already in the list
                    existing_steps = state[course_id]["sequences"][sequence_id]["activities"][activity_id]["steps"]
                    if not any(s["stepId"] == step_id for s in existing_steps):
                        step_entry = {
                            "stepId": step_id,
                            "answers": [
                                answer["responseId"]
                                for answer in step.get("postProcess", {}).get("successResponses", [])
                            ],
                        }
                        existing_steps.append(step_entry)
    return state


def work_for_me(data):
    # Load or initialize state
    state = load_state(STATE_FILE)
    state = initialize_state(data, state)

    for course in data:
        course_id = course['courseId']
        for sequence in course['sequences']:
            sequence_id = sequence['sequenceId']
            for activity in sequence['activities']:
                activity_id = activity['activityId']
                for step in activity['steps']:
                    step_id = step['stepId']
                    answers = step["answers"]

                    # Skip already processed steps
                    if step_id in state[course_id]["sequences"][sequence_id]["activities"][activity_id]["steps"]:
                        continue
                    answer_arg = [
                        {"answer": answer, "correct": True}
                        for answer in answers
                    ] if answers else []
                    addCorrectProgess(course_id, sequence_id, activity_id, step_id, answer_arg)

                    time.sleep(1)
                    addUsage(course_id)
                    time.sleep(30)

                    # Mark step as processed and save progress
                    state[course_id]["sequences"][sequence_id]["activities"][activity_id]["steps"].append(step_id)
                    save_state(state, STATE_FILE)
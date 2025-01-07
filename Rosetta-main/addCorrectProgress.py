import random
import requests # type: ignore
from datetime import datetime, timezone
import uuid

import config

utc_now = datetime.now(timezone.utc)

headers = {
    'Host': 'gaia-server.rosettastone.com',
    'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36',
    'Accept': '*/*',
    'Accept-Language': 'en-US,en;q=0.5',
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+config.TOKEN,
    'Origin': 'https://learn.rosettastone.com',
    'Sec-Fetch-Dest': 'empty',
    'Sec-Fetch-Mode': 'cors',
    'Sec-Fetch-Site': 'same-site',
    'Priority': 'u=0',
}


def addCorrectProgess(course, sequence, activity, step, answers):
    timestamp = utc_now.strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3] + "Z"
    data = {
        "operationName": "AddProgress",
        "variables": {
            "id": config.USER_ID,
            "messages": [
                {
                    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0",
                    "courseId": course,
                    "sequenceId": sequence,
                    "version": 2,
                    "activityId": activity,
                    "activityAttemptId": str(uuid.uuid4()),
                    "activityStepId": step,
                    "activityStepAttemptId": str(uuid.uuid4()),
                    "answers": answers,
                    "score": 1,
                    "skip": False,
                    "durationMs": config.DURATION,
                    "endTimestamp": timestamp
                }
            ]
        },
        "query": "mutation AddProgress($userId: String, $messages: [ProgressMessage!]!) {\n  progress(userId: $userId, messages: $messages) {\n    id\n    __typename\n  }\n}\n"
    }

    response = requests.post('https://gaia-server.rosettastone.com/graphql', headers=headers, json=data,
                             verify=False)
    if response.text.__contains__("Activity doesn't exist."):
        print("Course: ",course)
        print("Sequence: ",sequence)
        print("Activity: ",activity)
        print("Step: ",step)
    print(response.text)

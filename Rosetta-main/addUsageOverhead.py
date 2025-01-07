import requests
from datetime import datetime, timezone
import config

utc_now = datetime.now(timezone.utc)

headers = {
    'Host': 'gaia-server.rosettastone.com',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0',
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

def addUsage(cours_id):
    timestamp = utc_now.strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3] + "Z"
    data = {
        "operationName": "AddUsageOverhead",
        "variables": {
            "messages": [
                {
                    "id":config.USER_ID,
                    "userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0","learningContext":cours_id,
                    "durationMs":1000,
                    "endTimestamp":timestamp}
            ]
        },
        "query": "mutation AddProgress($userId: String, $messages: [ProgressMessage!]!) {\n  progress(userId: $userId, messages: $messages) {\n    id\n    __typename\n  }\n}\n"
    }

    requests.post('https://gaia-server.rosettastone.com/graphql', headers=headers, json=data, verify=False)

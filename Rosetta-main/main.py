import json

import config
from worker import work_for_me

if __name__ == "__main__":
    if config.LANG == "FR":
        with open('data/filtered.json', 'r') as file:
            filtered_data = json.load(file)
        work_for_me(filtered_data)
    elif config.LANG == "ENG":
        with open('data/filtered_eng.json', 'r') as file:
            filtered_data = json.load(file)
        work_for_me(filtered_data)
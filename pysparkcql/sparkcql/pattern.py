#command = {"id": 123}
#command = {"id": 123, "foo": [1, 2, 3]}

import json


command = {
    "id": 123,
    "foo": [1, 2, 3]
}

# match command:
#     case {"id": 123}:
#         print(command)
#     case {"id": 123, "foo": [1, 2, 3]}:
#         print(command)
#     case default:
#         print("not found")

print(json.dumps(command))
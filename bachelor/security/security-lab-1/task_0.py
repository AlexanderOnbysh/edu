import base64

# task file
with open('initial_task.txt') as f:
    text = f.read()

# double base64 encoding
decoded = base64.b64decode(base64.b64decode(text)).decode('utf-8')

# save result
with open('task_1.txt', 'w') as f:
    f.write(decoded)



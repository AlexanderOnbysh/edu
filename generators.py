# yield and yield from difference

# yield
def bottom():
    return (yield 42)

def middle():
    return (yield bottom())

def top():
    return (yield middle())

>> gen = top()
>> next(gen)
<generator object middle at 0x10478cb48>

# ----------------------
# yield from

# is roughly equivalent to
# ***
# for x in iterator:
#     yield x
# ***

def bottom():
    return (yield 42)

def middle():
    return (yield from bottom())

def top():
    return (yield from middle())

>> gen = top()
>> next(gen)
42

# ----------------------
# yield from not iterable
def test():
    yield from 10

>> gen = test()
>> next(gen)
TypeError: 'int' object is not iterable

# ----------------------
# yield from iterable object
def test():
    yield from [1, 2]

>> gen = test()
>> next(gen)
1
>> next(gen)
2
>> next(gen)
StopIteration:


# throw method in generators
# gen.throw(exception, value, traceback)
def test():
    while True:
        try:
            t = yield
            print(t)
        except Exception as e:
            print('Exception:', e)

>> gen = test()
>> next(gen)
>> gen.send(10)
10
>> gen.send(12)
12
>> gen.throw(TypeError, 18)
Exception: 18


# g.close() send GeneratorExit to GeneratorExit
def close(self):
    try:
        self.throw(GeneratorExit)
    except (GeneratorExit, StopIteration):
        pass
    else:
        raise RuntimeError("generator ignored GeneratorExit")
    # Other exceptions are not caught


# async

# Parallel async
# asyncio.gather put all tasks in event loop

import asyncio


async def bottom(name, sleep):
    await asyncio.sleep(sleep)
    print(f"I'm bottom {name}")
    return 42

async def middle(name, sleep):
    print(f"I'm middle {name}")
    await bottom(name, sleep)

async def top(name, sleep):
    print(f"I'm top {name}")
    await middle(name, sleep)


loop = asyncio.get_event_loop()
loop.run_until_complete(asyncio.gather(top('first', 3),
                                       top('second', 2),
                                       top('third', 1)
                                      ))

I'm top first
I'm middle first
I'm top third
I'm middle third
I'm top second
I'm middle second
# sleep for 3 seconds
I'm bottom third
I'm bottom second
I'm bottom first


# If we call corutine that call other corutine
# then task will be added sequatially to event loop
# and no parallelism happens
async def run():
    names = ['first', 'second', 'third']
    times = [3, 2, 1]
    for name, time in zip(names, times):
        await top(name, time)

loop = asyncio.get_event_loop()
loop.run_until_complete(run())

I'm top first
I'm middle first
# sleep for 3 seconds
I'm bottom first
I'm top second
I'm middle second
# sleep for 2 seconds
I'm bottom second
I'm top third
I'm middle third
# sleep for 1 seconds
I'm bottom third


# Create tasks from futures and put
# them to event loop
# await results from each task
async def run():
    loop = asyncio.get_event_loop()
    names = ['first', 'second', 'third']
    times = [3, 2, 1]
    tasks = []
    for name, time in zip(names, times):
        t = loop.create_task(top(name, time))
        tasks.append(t)

    await asyncio.gather(*tasks)
    # equvalet to
    # for task in tasks:
        # await task


loop = asyncio.get_event_loop()
loop.run_until_complete(run())
I'm top first
I'm middle first
I'm top second
I'm middle second
I'm top third
I'm middle third
# sleep for 1 seconds
I'm bottom third
# sleep for 1 seconds
I'm bottom second
# sleep for 1 seconds
I'm bottom first





# Call later
def hey_hey(n):
    print(n)

def hey():
    print('Hey!')
    loop = asyncio.get_event_loop()
    loop.call_later(10, lambda: hey_hey(42))


loop = asyncio.get_event_loop()
loop.call_later(20, hey)

# 20 seconds
Hey
# 10 seconds
42

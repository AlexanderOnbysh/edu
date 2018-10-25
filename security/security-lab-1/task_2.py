import binascii
from collections import Counter
from itertools import cycle
from string import digits, ascii_letters

from scipy.stats import entropy

cipher = '1c41023f564b2a130824570e6b47046b521f3f5208201318245e0e6b40022643072e13183e51183f5a1f3e4702245d4b285a1b23561965133f2413192e571e28564b3f5b0e6b50042643072e4b023f4a4b24554b3f5b0238130425564b3c564b3c5a0727131e38564b245d0732131e3b430e39500a38564b27561f3f5619381f4b385c4b3f5b0e6b580e32401b2a500e6b5a186b5c05274a4b79054a6b67046b540e3f131f235a186b5c052e13192254033f130a3e470426521f22500a275f126b4a043e131c225f076b431924510a295f126b5d0e2e574b3f5c4b3e400e6b400426564b385c193f13042d130c2e5d0e3f5a086b52072c5c192247032613433c5b02285b4b3c5c1920560f6b47032e13092e401f6b5f0a38474b32560a391a476b40022646072a470e2f130a255d0e2a5f0225544b24414b2c410a2f5a0e25474b2f56182856053f1d4b185619225c1e385f1267131c395a1f2e13023f13192254033f13052444476b4a043e131c225f076b5d0e2e574b22474b3f5c4b2f56082243032e414b3f5b0e6b5d0e33474b245d0e6b52186b440e275f456b710e2a414b225d4b265a052f1f4b3f5b0e395689cbaa186b5d046b401b2a500e381d61'


def decode(text, key):
    result = b''
    for char, key_letter in zip(text, cycle(key)):
        result += bytes([(char ^ ord(key_letter)), ])
    return result


def calculate_ioc(cipher):
    for step in range(1, 20):
        match = total = 0
        for i in range(len(cipher)):
            for j in range(i + step, len(cipher), step):
                total += 1
                if cipher[i] == cipher[j]:
                    match += 1

        ioc = float(match) / float(total)
        print("%3d%7.2f%% %s" % (step, 100*ioc, "#" * int(0.5 + 500*ioc)))


def count_symbols(text):
    allowed_symbols = set(ascii_letters)
    text = [c for c in text if c in allowed_symbols]
    occurrences = Counter()
    for c in ascii_letters:
        occurrences[c] = 0
    occurrences.update(text)
    length = len(text) if len(text) else 1e10
    frequencies = [(k, v / length) for k, v in occurrences.items()]
    return sorted(frequencies, reverse=True)


def get_monogram_distribution():
    d = {}
    with open('resources/monograms.txt') as f:
        for line in f:
            letter, counts = line.split()
            d[letter] = int(counts)
            d[letter.lower()] = int(counts)
    total = sum(d.values())

    frequencies = [(k, v / total) for k, v in d.items()]
    return sorted(frequencies, reverse=True)


calculate_ioc(cipher)

key_length = 3
task3_bytes = binascii.unhexlify(bytes(cipher, 'ascii'))
monogram_dist = [x[1] for x in get_monogram_distribution()]

key = []
for key_index in range(key_length):
    result = {}
    for i in digits + ascii_letters:
        t = [chr(c ^ ord(i)) for c in task3_bytes[key_index::key_length]]
        decoded_dist = [x[1] for x in count_symbols(t)]
        result[i] = entropy(decoded_dist, monogram_dist)
    s = sorted(result.items(), key=lambda x: x[1])[0]
    key.append(s[0])

print(key)
print(decode(task3_bytes, key))

with open('task_2.txt', 'w') as f:
    f.write(str(decode(task3_bytes, key)))

import itertools
import multiprocessing
import random
import string

cipher = 'EFFPQLEKVTVPCPYFLMVHQLUEWCNVWFYGHYTCETHQEKLPVMSAKSPVPAPVYWMVHQLUSPQLYWLASLFVWPQLMVHQLUPLRPSQLULQESPBLWPCSVRVWFLHLWFLWPUEWFYOTCMQYSLWOYWYETHQEKLPVMSAKSPVPAPVYWHEPPLUWSGYULEMQTLPPLUGUYOLWDTVSQETHQEKLPVPVSMTLEUPQEPCYAMEWWYOYULULTCYWPQLSEOLSVOHTLUYAPVWLYGDALSSVWDPQLNLCKCLRQEASPVILSLEUMQBQVMQCYAHUYKEKTCASLFPYFLMVHQLUHULIVYASHEUEDUEHQBVTTPQLVWFLRYGMYVWMVFLWMLSPVTTBYUNESESADDLSPVYWCYAMEWPUCPYFVIVFLPQLOLSSEDLVWHEUPSKCPQLWAOKLUYGMQEUEMPLUSVWENLCEWFEHHTCGULXALWMCEWETCSVSPYLEMQYGPQLOMEWCYAGVWFEBECPYASLQVDQLUYUFLUGULXALWMCSPEPVSPVMSBVPQPQVSPCHLYGMVHQLUPQLWLRPHEUEDUEHQMYWPEVWSSYOLHULPPCVWPLULSPVWDVWGYUOEPVYWEKYAPSYOLEFFVPVYWETULBEUF'

with open('resources/top_1000_eng.txt') as file:
    TOP_1000_ENG = file.read().strip().split('\n')
    TOP_1000_ENG = [w.upper() for w in TOP_1000_ENG if len(w) > 2]


def read_ngrams(filename):
    with open(filename) as file:
        ngrams = file.read().strip().split('\n')
    ngrams = dict(x.split(' ') for x in ngrams[:1000])
    ngrams = {k: int(v) for k, v in ngrams.items()}
    total = sum(v for v in ngrams.values())
    return {k: (v / total) for k, v in ngrams.items()}


MONOGRAMS = read_ngrams('resources/monograms.txt')
BIGRAMS = read_ngrams('resources/bigrams.txt')
TRIGRAMS = read_ngrams('resources/trigrams.txt')
QUADGRAM = read_ngrams('resources/quadgrams.txt')

NGRAMS = {
    1: MONOGRAMS,
    2: BIGRAMS,
    3: TRIGRAMS,
    4: QUADGRAM
}


POPULATION_SIZE = 400
AMOUNT_OF_GENERATIONS = 150
MUTATION_PROBABILITY = 0.1


def initial_population(population_size):
    alphabet = string.ascii_uppercase
    return [''.join(random.sample(alphabet, len(alphabet))) for _ in range(population_size)]


def find_word_occurencies(text):
    def word_score(word):
        len_word = len(word)
        if len_word > 7:
            return 4
        elif len_word > 3:
            return 2
        else:
            return 1

    return sum(
        word_score(word) if text.find(word) >= 0 else 0
        for word in TOP_1000_ENG
    )


def find_ngrams_amount(text, n):
    grams = {}
    chars_to_pick = zip(*[text[i:] for i in range(n)])
    for gram in chars_to_pick:
        gram = ''.join(gram)
        if gram not in grams:
            grams[gram] = 0

        grams[gram] += 1

    return grams


def compare_amounts(compared, n):
    ideal = NGRAMS[n]
    total_compared = sum(compared[char] for char in compared.keys())

    final_score = 0
    for char, ideal_freq in ideal.items():
        final_score += abs(ideal_freq - (compared.get(char, 0) / total_compared))

    return -final_score


def fitness(text, key):
    result_text = apply_key(text, key)

    monograms_amount = find_ngrams_amount(result_text, 1)
    bigrams_amount = find_ngrams_amount(result_text, 2)
    trigrams_amount = find_ngrams_amount(result_text, 3)
    quadgrams_amount = find_ngrams_amount(result_text, 4)

    s1 = compare_amounts(monograms_amount, 1)  # * 9
    s2 = compare_amounts(bigrams_amount, 2)  # * 4
    s3 = compare_amounts(trigrams_amount, 3)  # * 5
    s4 = compare_amounts(quadgrams_amount, 4)
    score = s1 + s2 + s3 + s4

    return score


def apply_key(text, key):
    alphabet = string.ascii_uppercase

    result_text = ''
    for char in text:
        result_text += alphabet[key.index(char)]

    return result_text


def crossover_parents(main, secondary, line):
    first_part = main[:line]
    second_part = secondary[line:]

    child = list(first_part + second_part)
    extra = set(secondary[:line]) - set(child)

    for i, char in enumerate(child):
        if i >= line and child.count(char) > 1:
            child[i] = extra.pop()

    assert len(child) == len(set(child))
    return child


def crossover(parent1, parent2):
    crossover_line = random.randint(1, len(parent1) - 1)
    child1 = crossover_parents(parent1, parent2, crossover_line)
    child2 = crossover_parents(parent2, parent1, crossover_line)

    for i in range(len(parent1)):
        if random.random() < MUTATION_PROBABILITY:
            picked_char = child1[i]
            random_char = random.choice(child1)
            random_char_i = child1.index(random_char)
            child1[i] = random_char
            child1[random_char_i] = picked_char

        if random.random() < MUTATION_PROBABILITY:
            picked_char = child2[i]
            random_char = random.choice(child1)
            random_char_i = child2.index(random_char)
            child2[i] = random_char
            child2[random_char_i] = picked_char

    return ''.join(child1), ''.join(child2)


def pick_by_roulette(population, total_sum):
    wheel_result = random.uniform(0, round(total_sum, 5))
    accumulator = 0
    for key, score in population:
        accumulator += score
        if accumulator >= wheel_result:
            return key

    return population[-1][0]


def create_pair(pair):
    return pair[1], fitness(pair[0], pair[1])


def calculate_fitnesses(text, current_generation):
    with multiprocessing.Pool(10) as pool:
        return pool.map(create_pair, zip(itertools.cycle([text, ]), current_generation))


current_generation = initial_population(POPULATION_SIZE)
for i in range(AMOUNT_OF_GENERATIONS):
    with_score = calculate_fitnesses(cipher, current_generation)
    with_score.sort(key=lambda pair: pair[1], reverse=True)

    best = with_score[0][1]
    print(f'Generation: {i} Fitness: {best}')

    sum_of_fitness = sum(pair[1] for pair in with_score)
    childs = []
    for _ in range(POPULATION_SIZE // 2):
        parent1 = pick_by_roulette(with_score, sum_of_fitness)
        parent2 = pick_by_roulette(with_score, sum_of_fitness)

        childs.extend(crossover(parent1, parent2))

    current_generation = childs

print('Top 10 keys:')
current_generation.sort(key=lambda key: fitness(cipher, key), reverse=True)
for key in current_generation[:10]:
    print(f'Key: {key}')
    print('Text: ', apply_key(cipher, key))

with open('task_3.txt', 'w') as f:
    f.write(apply_key(cipher, current_generation[0]))

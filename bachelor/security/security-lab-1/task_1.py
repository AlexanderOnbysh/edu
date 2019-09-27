cipher = ']|d3gaj3r3avcvrgz}t>xvj3K\A3pzc{va=3V=t=3zg3`{|f.w3grxv3r3`gaz}t31{v..|3d|a.w13r}w?3tzev}3g{v3xvj3z`31xvj1?3k|a3g{v3uza`g3.vggva31{13dzg{31x1?3g{v}3k|a31v13dzg{31v1?3g{v}31.13dzg{31j1?3r}w3g{v}3k|a3}vkg3p{ra31.13dzg{31x13rtrz}?3g{v}31|13dzg{31v13r}w3`|3|}=3J|f3~rj3f`v3z}wvk3|u3p|z}pzwv}pv?3[r~~z}t3wz`gr}pv?3Xr`z`xz3vkr~z}rgz|}?3`grgz`gzpr.3gv`g`3|a3d{rgveva3~vg{|w3j|f3uvv.3d|f.w3`{|d3g{v3qv`g3av`f.g='
mean_space_frequency = 0.172


def space_statistics(string):
    return string.count(' ') / len(string)
    

results = []
for i in range(26):
    res = ''.join(chr(ord(char) ^ i) for char in cipher)
    results.append((abs(mean_space_frequency - space_statistics(res)), res))

results.sort()

print(f'Found text with minimal space statistic diff: {results[0][0]}\n'
      f'{results[0][1]}')

with open('task_2.txt', 'w') as f:
    f.write(results[0][1].replace('=', 'l'))

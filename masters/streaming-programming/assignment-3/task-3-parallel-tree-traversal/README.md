# Parallel tree traversal

As we can see parallelization of tree traversal improves speed only in case of big trees. For small trees non-parallel solution is faster. This is due the overhead on thread creation.
```text
Generating tree...
Run perf test...
Non parallel implementation of tree sum (Depth = 10): 0.877714 ms
Run perf test...
Parallel implementation of tree sum (Depth = 10): 1.1524349999999999 ms

Generating tree...
Run perf test...
Non parallel implementation of tree sum (Depth = 23): 570.789076 ms
Run perf test...
Parallel implementation of tree sum (Depth = 23): 209.236101 ms
```
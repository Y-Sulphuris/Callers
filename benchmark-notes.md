Java >= 9 (StackWalker):
```
Benchmark  Mode  Cnt     Score    Error  Units
Bench.get  avgt    5  1678.194 ± 37.339  ns/op
```

Java <= 8 - sun.reflect:
```
Benchmark   Mode  Cnt   Score    Error  Units
Bench.get   avgt    5  96.533 ± 96.255  ns/op
```
Java <= 8 - Throwable (the slowest, if nothing else is available)
```
Benchmark   Mode  Cnt     Score     Error  Units
Bench.get   avgt    5  8140.835 ± 231.353  ns/op
```
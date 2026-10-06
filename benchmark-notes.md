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
Java <= 1.3 - ThrowableLegacy (actually the slowest, but you will never see it in java 1.4 or newer)
```
Benchmark  Mode  Cnt      Score     Error  Units
Bench.get  avgt    5  11740.109 ± 193.148  ns/op
```
#include <stdlib.h>
#include <stdio.h>

void *mem_alloc(size_t);
void *mem_realloc(void*, size_t);
void mem_free(void *);
void mergeBlocks();
void dump();

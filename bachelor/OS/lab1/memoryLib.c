#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <string.h>

#include "memoryLib.h"

// describing structure of allocated element (Block)
struct TBlock {
    struct TBlock *next;
    size_t size;
    int free;
};

#define META_SIZE sizeof(struct TBlock)

typedef struct TBlock TBlock;

// head of list
void *header = NULL;

// check if given size belongs to power of two (e.g 1, 2, 4, 8, 16, 32...)
bool isPowerOfTwo(size_t size) {

    if (size == 0)
        return false;

    while (size != 1)
    {
        if (size % 2 != 0)
            return false;
        size = size / 2;
    }
    return true;
}

// compute the next power of two (e.g size=5, function return 8)
size_t getNextPowerOfTwo(size_t size) {

    if (isPowerOfTwo(size)) {
        return size;
    }

    printf("\n(given) Size: %zu\n", size);
    size -= 1;
    size = size | (size >> 1);
    size = size | (size >> 2);
    size = size | (size >> 4);
    size = size | (size >> 8);
    size = size | (size >> 16);
    size = size | (size >> 32);
    size = size | (size >> 64);
    size += 1;
    printf("(changed to) Size: %zu\n", size);
    return size;
}

// find in the list free block, if not found, return null
TBlock *getFreeBlock(TBlock **iterPtr, size_t size) {
    TBlock *current = header;

    while (current && !(current->free && current->size >= size)) {
        *iterPtr = current;
        current = current->next;
    }

    return current;
}

// in case of lack of memory, it allocates new memory
TBlock *allocateSpace(TBlock *iterPtr, size_t size) {
    // find the program break
    TBlock *block = sbrk(0);

    void *allocatedMemory = sbrk(META_SIZE + size);

    // if it's failed
    if (allocatedMemory == (void*) -1) {
        return NULL;
    }

    // set next block for current block
    if (iterPtr) {
        iterPtr->next = block;
    }

    block->size = size;
    block->next = NULL;
    block->free = 0;

    return block;
}

// split given block if it's possible
TBlock *splitBlock(TBlock *current, size_t size) {

    // if required size equals to the current
    if (size == current->size) {
        return current;
    }

    // split size divided by two
    size_t temp_s = current->size;
    while (temp_s / 2 >= size) {
        temp_s = temp_s / 2;
    }

    // split block and configure relationships
    TBlock *leftBlock = current + size;
    leftBlock->next = current->next;
    leftBlock->size = current->size - size;
    leftBlock->free = 1;

    current->next = leftBlock;
    current->size = size ;

    return current;
}

// allocate memory
void *mem_alloc(size_t size) {

    TBlock *block;

    // align given size
    size = getNextPowerOfTwo(size);

    if (size <= 0) {
        return NULL;
    }

    // in case of first call
    if (!header) {
        block = allocateSpace(NULL, size);
        if (!block) {
            return NULL;
        }
        header = block;
    } else {
        TBlock *iterPtr = header;
        block = getFreeBlock(&iterPtr, size);

        // if free block was not found, allocate more space,
        // otherwise try to split given free block
        if (!block) {
            block = allocateSpace(iterPtr, size);

            if (!block) {
                return NULL;
            }

        } else {
            // split block
            block = splitBlock(block, size);
            block->free = 0;
        }
    }
    // +1 increments the address by one sizeof(struct(block_meta)).
    return(block+1);
}

// get meta block of current data block
TBlock *getBlock(void *ptr) {
    return (TBlock*)ptr - 1;
}

// merge free blocks if it's possible
void mergeBlocks() {

    TBlock *iterPtr = header;
    TBlock *curr = getFreeBlock(&iterPtr, 0);

    // if there is no free block
    if (!curr) {
        return;
    }

    // if next block is used
    if (!curr->next->free) {
        return;
    }

    TBlock *newBlock = curr;
    size_t total_size = 0;

    while(curr && (curr->free == 1)) {
        newBlock->next = curr->next;
        total_size += curr->size;
        curr = curr->next;
    }

    newBlock->free = 1;
    newBlock->size = total_size;
}

// free memory
void mem_free(void *addr) {

    if (!addr) {
        return;
    }

    TBlock *block_ptr = getBlock(addr);

    block_ptr->free = 1;

    // merge free blocks
    mergeBlocks();
}

// reallocate memory by given address
void *mem_realloc(void *addr, size_t size) {

    size = getNextPowerOfTwo(size);

    // if size was not given
    if (!size)
        return NULL;

    // free memory by given address
    mem_free(addr);

    // allocate new memory with given size
    void *new_addr = mem_alloc(size);


    // rewrite everything to the new allocated memory
    memmove(new_addr, addr, size);

    return new_addr;
}

// print out current state of memory
void dump(){
    printf("\nSummary of memory:\n");
    TBlock *current = header;
    printf("----------------------\n");
    while (current) {
        printf("Address: %p -- Size: %zu -- Free: %s -- Next Block: %p\n", current, current->size, current->free ? "Yes" : "No", current->next);
        current = current->next;
    }
    printf("----------------------\n");
}

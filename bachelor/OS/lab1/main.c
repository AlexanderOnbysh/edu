#include <stdio.h>
#include <string.h>
#include "memoryLib.h"

int main() {
    dump();

    char *testChar = mem_alloc(17);
    int *testInt = mem_alloc(sizeof(int));
    int *testInt_ = mem_alloc(sizeof(int));

    *testInt = 27;
    *testInt_ = 14;
    strcpy(testChar, "hello world!");

    printf("\nNew allocated variables: \n");
    printf("TestChar: %s\n", testChar);
    printf("TestInt: %d\n", *testInt);
    printf("TestInt: %d\n", *testInt_);

    dump();

    mem_free(testInt);

    dump();

    testChar = mem_realloc(testChar, 33);
    strcpy(testChar, "New sentence!");

    printf("TestChar: %s\n", testChar);
    dump();
}
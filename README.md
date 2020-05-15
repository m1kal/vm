# A simple virtual machine in ClojureScript with Reagent

This is a toy project to teach children how microprocessors work. There are only 8 implemented instructions, 8 registers, the program code can consist of up to 32 instructions.

Both the UI and instructions are available in Polish and in English.

There is no such concept as machine code - the instructions are evaluated.

The result is available at https://m1kal.github.io/vm/resources/public/

Ideas for improvement:
- Integrate program counter to registers to allow more flexible flow control
- Add data memory
- Find a way to expand program memory while keeping the UI clear.

Example program to multiply two numbers using addition in a loop:
```set 1 4
set 2 6
copy 2 3
copy 1 2
dec 3
dec 3
eval
copy 8 2
if-zero 3 goto 16
goto 6
```

To run in development mode:
1. Clone the repo
2. Make sure Leiningen is installed
3. Run `lein figwheel`
4. Go to http://localhost:3449

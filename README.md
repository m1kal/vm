# A simple virtual machine in ClojureScript with Reagent

This is a toy project to teach children how microprocessors work. There are only 7 implemented instructions, 8 registers, the program code can consist of up to 32 instructions.

Both the UI and instructions are available in Polish and in English.

There is no such concept as machine code - the instructions are evaluated.

Ideas for improvement:
- Integrate program counter to registers to allow more flexible flow control
- Add conditionals
- Add data memory
- Find a way to expand program memory while keeping the UI clear.

To run in development mode:
1. Clone the repo
2. Make sure Leiningen is installed
3. Run `lein figwheel`
4. Go to http://localhost:3449

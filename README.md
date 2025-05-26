# PL/0 Interpreter

A Java implementation of an interpreter for the PL/0 programming language. PL/0 is a simplified version of Pascal designed for educational purposes. For more info on the syntax, see [the Wikipedia entry](https://en.wikipedia.org/wiki/PL/0).

## Features

- Handwriten lexical analyser, recursive descent parser, and runtime interpreter
- Full implementation of the langage
- Uses `!` to print and `?` to read (see `calculator.pl0` for example)

## Building

To build and pack the interpreter, simply run:
```sh
make package
```

## Usage

The interpreter is run like any other Java program, and takes the script to execute as argument:
```sh
java -jar interpreter.jar programs/primes.pl0
```
Some example programs are in the `program` folder.

## Limitations

- Very simple langage, limited to integers
- No variable shadowing between global and procedure scope

## Licence

[MIT License](LICENSE)
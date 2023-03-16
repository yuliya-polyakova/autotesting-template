# Code formatting requirements
Indentation must be strictly 4 spaces (not a tab character).

## Packages
Package names use only lowercase letters

## Classes
Class names in Java must begin with a capital letter, the compound name is written together, each word is capitalized

Class names containing tests must end with Test

The class responsible for the UI page must end with Page;
for components on Tabs, Menu;
for panels on Panel, Footer

## Class fields
The first letter must be lowercase, the first letters of internal words must be uppercase.

Constant names are made up of all capital letters separated into words by underscores.

The class field responsible for the input element in the name must indicate the type of the field (for example, areaNameInput, saveButton).
For the template codes for fields, you can use the utility ***GenerateSeleniumField***, specifying the field name and type, the field declaration code and methods of interaction with it are formed

## Class methods
The first letter must be lowercase, the first letters of internal words must be uppercase.

First word set, click, check... verb, then element name or logical unit

If a condition is required, add "By" and the condition itself, for example `clickCustomerByName`

## Class structure
1. Class constants
2. Class fields and methods of interaction with it
   1. For Page classes, the order of the fields must correspond to the order of the elements on the screen (top to bottom, left to right)
3. Class constructor

## Variables inside a method
Variable names must start with a lowercase letter, internal words with a capital letter.

Variables are declared as close as possible to the place of use.

## Resources
Words in multi-word names are separated by underscores.

# Object Oriented Programming
OOP principles should be applied to structure and make code easier to read,
to reduce duplication in reuse, you must use the generated implementation of the code
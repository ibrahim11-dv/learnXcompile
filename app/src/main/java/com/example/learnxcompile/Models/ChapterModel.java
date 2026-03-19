package com.example.learnxcompile.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.learnxcompile.Items.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterModel extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "learnXcompile";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_NAME = "chapters";

    public ChapterModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE languages (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, language_id INTEGER, title TEXT, description TEXT, order_index INTEGER, is_locked INTEGER, FOREIGN KEY (language_id) REFERENCES languages(id))");
        db.execSQL("CREATE TABLE steps (id INTEGER PRIMARY KEY AUTOINCREMENT, chapter_id INTEGER, type TEXT, order_index INTEGER, FOREIGN KEY (chapter_id) REFERENCES chapters(id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE explanation_content (id INTEGER PRIMARY KEY AUTOINCREMENT, step_id INTEGER NOT NULL, content_text TEXT, did_you_know TEXT, key_points TEXT, FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE code_example_content (id INTEGER PRIMARY KEY AUTOINCREMENT, step_id INTEGER NOT NULL, language_tag TEXT, code_snippet TEXT, expected_output TEXT, context_text TEXT, FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE qcm_content (id INTEGER PRIMARY KEY AUTOINCREMENT, step_id INTEGER NOT NULL, question TEXT, option_a TEXT, option_b TEXT, option_c TEXT, option_d TEXT, correct_option TEXT, explanation TEXT, FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE test_content (id INTEGER PRIMARY KEY AUTOINCREMENT, step_id INTEGER NOT NULL, instructions TEXT, hint TEXT, starter_code TEXT, expected_output TEXT, test_cases TEXT, FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE)");

        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            long javaId = insertLanguage(db, "Java", "A class-based, object-oriented programming language");
            insertJavaChapters(db, javaId);

            long pythonId = insertLanguage(db, "Python", "A high-level, interpreted programming language");
            insertPythonChapters(db, pythonId);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertJavaChapters(SQLiteDatabase db, long langId) {
        // 1. Intro
        long c1 = insertChapter(db, langId, "Introduction to Java", "JVM and your first program", 1, 0);
        insertFullStep(db, c1, 
            "Java is a high-level, class-based, object-oriented programming language designed to have as few implementation dependencies as possible. It is a general-purpose programming language intended to let application developers write once, run anywhere (WORA), meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.", 
            "Java was originally called 'Oak' after an oak tree that stood outside James Gosling's office.",
            "Write Once, Run Anywhere (WORA)|Uses the JVM (Java Virtual Machine)|Object-Oriented Architecture|Statically Typed",
            "Java", "public class Main {\n  public static void main(String[] args) {\n    System.out.println(\"Hello Java\");\n  }\n}", "Hello Java", "Every Java application must have a main method which is the entry point for execution.",
            "What is the entry point of every Java program?", "public void start()", "public static void main(String[] args)", "void run()", "static void main()", "B", "The JVM looks for the main method signature to start the app.",
            "Print 'Hello Java' using System.out.println().", "Use System.out.println(\"text\");", "public class Main {\n  public static void main(String[] args) {\n    // Write here\n  }\n}", "Hello Java"
        );

        // 2. Variables
        long c2 = insertChapter(db, langId, "Variables & Data Types", "Storing and naming data", 2, 1);
        insertFullStep(db, c2,
            "Variables are containers for storing data values. In Java, there are different types of variables, for example: String (stores text), int (stores integers), float (stores floating point numbers), char (stores single characters), and boolean (stores true or false values).",
            "Java is strictly typed, meaning you must declare the variable type upfront.",
            "int for whole numbers|double for decimal precision|String for text|boolean for logical states",
            "Java", "int myNum = 15;\nmyNum = 20;\nSystem.out.println(myNum);", "20", "Variables can be updated by assigning a new value to them using the '=' operator.",
            "Which type is used for decimal numbers like 19.99?", "int", "double", "char", "boolean", "B", "double is used for floating point numbers.",
            "Declare int x = 5 and print it.", "Use int x = 5; then println(x);", "public class Main {\n  public static void main(String[] args) {\n    // Write here\n  }\n}", "5"
        );

        // 3. Operators
        long c3 = insertChapter(db, langId, "Java Operators", "Arithmetic and Logic", 3, 1);
        insertFullStep(db, c3,
            "Operators are used to perform operations on variables and values. Java divides the operators into the following groups: Arithmetic operators (+, -, *, /, %), Assignment operators (=, +=), Comparison operators (==, !=, >, <), and Logical operators (&&, ||, !).",
            "The modulo operator (%) returns the division remainder.",
            "Arithmetic for math|Comparison for true/false|Logical for combined conditions|Assignment for setting values",
            "Java", "int x = 10 % 3;\nSystem.out.println(x);", "1", "The remainder of 10 / 3 is 1.",
            "What does '&&' represent in Java?", "OR", "NOT", "AND", "ADD", "C", "&& is the logical AND operator.",
            "Print the result of 10 + 20.", "System.out.println(10 + 20);", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "30"
        );

        // 4. Strings
        long c4 = insertChapter(db, langId, "Strings", "Text manipulation", 4, 1);
        insertFullStep(db, c4,
            "A String in Java is actually an object, which contain methods that can perform certain operations on strings. For example, length(), toUpperCase(), toLowerCase(), and indexOf(). You can concatenate strings using the '+' operator.",
            "Strings are immutable in Java, meaning once created, they cannot be changed.",
            "String length via .length()|Concatenation with +|Case conversion methods|Indexing starts at 0",
            "Java", "String txt = \"Hello\";\nSystem.out.println(txt.length());", "5", "The length() method returns the number of characters.",
            "How to find the length of a string 's'?", "s.size()", "s.count()", "s.length()", "s.len()", "C", "Strings use the length() method.",
            "Print 'Hi Ali' by joining 'Hi ' and 'Ali'.", "Use 'Hi ' + 'Ali'", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "Hi Ali"
        );

        // 5. Math
        long c5 = insertChapter(db, langId, "Java Math", "Common math functions", 5, 1);
        insertFullStep(db, c5,
            "The Java Math class has many methods that allows you to perform mathematical tasks on numbers. Common methods include Math.max(x, y), Math.min(x, y), Math.sqrt(x), Math.abs(x), and Math.random().",
            "Math methods are static, so you don't need to create a Math object.",
            "Math.max finds the highest value|Math.sqrt for square roots|Math.abs for positive values|Math.random for 0.0 to 1.0",
            "Java", "System.out.println(Math.max(5, 10));", "10", "Math.max(x, y) returns the value with the highest magnitude.",
            "What is Math.sqrt(64)?", "8.0", "64", "32", "16", "A", "Square root of 64 is 8.",
            "Print the absolute value of -5.", "Use Math.abs(-5)", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "5"
        );

        // 6. Booleans
        long c6 = insertChapter(db, langId, "Booleans", "Logic and truth", 6, 1);
        insertFullStep(db, c6,
            "Very often, in programming, you will need a data type that can only have one of two values, like: YES / NO, ON / OFF, TRUE / FALSE. For this, Java has a boolean data type, which can take the values true or false.",
            "Boolean expressions use comparison operators to return a truth value.",
            "Only true or false|Used for conditional testing|Results from comparisons|Logical basis of flow",
            "Java", "int x = 10;\nSystem.out.println(x == 10);", "true", "The comparison returns a boolean value.",
            "What is the result of 10 > 9?", "true", "false", "1", "0", "A", "10 is indeed greater than 9.",
            "Print 'false' by comparing 5 > 10.", "System.out.println(5 > 10);", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "false"
        );

        // 7. If...Else
        long c7 = insertChapter(db, langId, "If...Else", "Conditional statements", 7, 1);
        insertFullStep(db, c7,
            "Use if to specify a block of code to be executed, if a specified condition is true. Use else to specify a block of code to be executed, if the same condition is false. Use else if to specify a new condition to test, if the first condition is false.",
            "Indentation doesn't affect logic in Java, but curly braces {} define blocks.",
            "if for true condition|else for false outcome|else if for chaining|Short hand ternary available",
            "Java", "int x = 20;\nif (x > 18) {\n  System.out.println(\"Old\");\n} else {\n  System.out.println(\"Young\");\n}", "Old", "The if block runs because 20 > 18.",
            "Keyword for alternative condition?", "else if", "elseif", "otherwise", "elif", "A", "Java uses 'else if' with a space.",
            "If 10 > 5 print 'Yes' else 'No'.", "Use if else", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "Yes"
        );

        // 8. Switch
        long c8 = insertChapter(db, langId, "Switch", "Choosing from many", 8, 1);
        insertFullStep(db, c8,
            "Instead of writing many if..else statements, you can use the switch statement. The switch statement selects one of many code blocks to be executed. The break keyword breaks out of the switch block. The default keyword specifies some code to run if there is no case match.",
            "Switch works with byte, short, char, int, Enums, and Strings.",
            "case for matching|break to stop execution|default for fall-back|Clean multi-branch logic",
            "Java", "int day = 4;\nswitch (day) {\n  case 4: System.out.println(\"Thu\"); break;\n  default: System.out.println(\"Wait\");\n}", "Thu", "The case matching 4 is executed.",
            "What stops a switch case from continuing?", "stop", "end", "break", "exit", "C", "break is essential to stop falling through.",
            "Switch on x=1, case 1 print 'One'.", "Use switch case 1", "public class Main {\n  public static void main(String[] args) {\n    int x = 1;\n    // Code here\n  }\n}", "One"
        );

        // 9. While Loop
        long c9 = insertChapter(db, langId, "While Loop", "Conditional repetition", 9, 1);
        insertFullStep(db, c9,
            "Loops can execute a block of code as long as a specified condition is reached. The while loop loops through a block of code as long as a specified condition is true. The do/while loop is a variant that executes the block once before checking the condition.",
            "Always ensure the condition eventually becomes false to avoid infinite loops.",
            "while checks then runs|do-while runs then checks|Condition must be boolean|Increment inside body",
            "Java", "int i = 0;\nwhile (i < 2) {\n  System.out.println(i);\n  i++;\n}", "0\n1", "The loop runs while i is less than 2.",
            "Which loop runs at least once?", "while", "for", "do-while", "for-each", "C", "do-while checks at the end.",
            "While i < 3 print i then i++.", "Initialize i=0", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "0\n1\n2"
        );

        // 10. For Loop
        long c10 = insertChapter(db, langId, "For Loop", "Counted repetition", 10, 1);
        insertFullStep(db, c10,
            "When you know exactly how many times you want to loop through a block of code, use the for loop instead of a while loop. Syntax: for (statement 1; statement 2; statement 3) { code block }. There is also a \"for-each\" loop, which is used exclusively to loop through elements in an array.",
            "Statement 1 is initialization, 2 is condition, 3 is update.",
            "Compact syntax|Ideal for arrays|for-each for simplicity|Nested loops possible",
            "Java", "for (int i = 0; i < 3; i++) {\n  System.out.println(i);\n}", "0\n1\n2", "Classic 3-part for loop.",
            "How to iterate 10 times?", "for(i=0; i<10; i++)", "while(10)", "loop(10)", "repeat(10)", "A", "Standard for loop syntax.",
            "Print 'Hi' 2 times using for loop.", "Loop i=0 to 1", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "Hi\nHi"
        );

        // 11. Arrays
        long c11 = insertChapter(db, langId, "Arrays", "Lists of items", 11, 1);
        insertFullStep(db, c11,
            "Arrays are used to store multiple values in a single variable, instead of declaring separate variables for each value. To declare an array, define the variable type with square brackets []. Array indexes start with 0: [0] is the first element. [1] is the second element, etc.",
            "The length of an array is fixed after it is created.",
            "0-indexed access|Fixed size|Accessed via [index]|.length for size",
            "Java", "String[] cars = {\"Volvo\", \"BMW\"};\nSystem.out.println(cars[0]);", "Volvo", "Index 0 retrieves the first element.",
            "Index of the second element?", "0", "1", "2", "-1", "B", "Index starts at 0, so 1 is the second.",
            "Create array {1, 2} print length.", "Use .length", "public class Main {\n  public static void main(String[] args) {\n    // Code here\n  }\n}", "2"
        );

        // 12. Methods
        long c12 = insertChapter(db, langId, "Methods", "Reusable functions", 12, 1);
        insertFullStep(db, c12,
            "A method is a block of code which only runs when it is called. You can pass data, known as parameters, into a method. Methods are used to perform certain actions, and they are also known as functions. Why use methods? To reuse code: define the code once, and use it many times.",
            "In Java, methods must be declared within a class.",
            "void means no return|Parameters passed in ()|Return values via return keyword|Static belongs to class",
            "Java", "static void myMethod() {\n  System.out.println(\"Hello\");\n}\npublic static void main(String[] args) {\n  myMethod();\n}", "Hello", "Method is defined once and called in main.",
            "Keyword for method returning nothing?", "null", "empty", "void", "static", "C", "void indicates the method doesn't return a value.",
            "Define method 'sayHi' and call it.", "Print 'Hi' inside", "public class Main {\n  // Method here\n  public static void main(String[] args) {\n    // Call here\n  }\n}", "Hi"
        );
    }

    private void insertPythonChapters(SQLiteDatabase db, long langId) {
        // 1. Intro
        long c1 = insertChapter(db, langId, "Introduction to Python", "The power of simplicity", 1, 0);
        insertFullStep(db, c1,
            "Python is a popular programming language. It was created by Guido van Rossum, and released in 1991. It is used for web development, software development, mathematics, and system scripting. Python works on different platforms (Windows, Mac, Linux, Raspberry Pi, etc.). Python has a simple syntax similar to the English language.",
            "Python was named after 'Monty Python's Flying Circus'.",
            "Readable Syntax|Indentation based|Large Standard Library|High-level language",
            "Python", "print(\"Hello Python\")", "Hello Python", "Python uses simple functions like print() for output, no class boilerplate needed.",
            "How do you output 'Hello' in Python?", "System.out.println()", "printf()", "print(\"Hello\")", "echo", "C", "Python uses the built-in print() function.",
            "Write a Python print for 'Hi'.", "print('Hi')", "# Write here\n", "Hi"
        );

        // 2. Syntax
        long c2 = insertChapter(db, langId, "Python Syntax", "Indentation matters", 2, 1);
        insertFullStep(db, c2,
            "Python indentation is a way of telling a Python interpreter that the group of statements belongs to a particular block of code. Indentation refers to the spaces at the beginning of a code line. Where in other programming languages the indentation in code is for readability only, the indentation in Python is very important.",
            "Python will give you an error if you skip indentation.",
            "Indentation defines blocks|No curly braces needed|Spaces usually count as 4|Consistent depth required",
            "Python", "if 5 > 2:\n    print(\"Five is greater than two!\")", "Five is greater than two!", "The indented print line belongs to the if block.",
            "What defines a code block in Python?", "Curly braces {}", "Indentation", "Semicolons", "Parentheses ()", "B", "Python uniquely uses indentation for structure.",
            "If 1 < 2 print 'Yes' with indent.", "4 spaces indent", "if 1 < 2:\n", "Yes"
        );

        // 3. Comments
        long c3 = insertChapter(db, langId, "Comments", "Documenting code", 3, 1);
        insertFullStep(db, c3,
            "Comments can be used to explain Python code. Comments can be used to make the code more readable. Comments can be used to prevent execution when testing code. Comments starts with a #, and Python will ignore them.",
            "Python does not really have a syntax for multi-line comments.",
            "# starts a comment|Ignored by interpreter|Can be on separate lines|Can be at end of code",
            "Python", "# This is a comment\nprint(\"Hello\")", "Hello", "The line starting with # is not executed.",
            "Symbol for Python comments?", "//", "/*", "#", "<!--", "C", "# is the comment symbol.",
            "Print 'Ok' with a comment above.", "# A comment here", "# Comment\n", "Ok"
        );

        // 4. Variables
        long c4 = insertChapter(db, langId, "Python Variables", "Dynamic typing", 4, 1);
        insertFullStep(db, c4,
            "Variables are containers for storing data values. Python has no command for declaring a variable. A variable is created the moment you first assign a value to it. Variables do not need to be declared with any particular type, and can even change type after they have been set.",
            "Variable names are case-sensitive (age, Age and AGE are three different variables).",
            "No type declaration|Case-sensitive names|Dynamic reassignment|Created on assignment",
            "Python", "x = 5\nx = \"Ali\"\nprint(x)", "Ali", "The variable x changes from int to string.",
            "Is 'x = 5' valid in Python?", "Yes", "No", "Only in functions", "Only with var", "A", "Python allows direct assignment.",
            "Set x=10 and print it.", "x = 10 then print(x)", "", "10"
        );

        // 5. Data Types
        long c5 = insertChapter(db, langId, "Data Types", "Built-in types", 5, 1);
        insertFullStep(db, c5,
            "In programming, data type is an important concept. Variables can store data of different types, and different types can do different things. Built-in types include: Text Type (str), Numeric Types (int, float, complex), Sequence Types (list, tuple, range), Mapping Type (dict), and Boolean Type (bool).",
            "You can get the data type of any object by using the type() function.",
            "str for strings|int for integers|float for decimals|list for collections",
            "Python", "x = 5\nprint(type(x))", "<class 'int'>", "The type() function tells you it is an integer.",
            "Type of \"Hello\"?", "int", "str", "float", "list", "B", "Text is represented by the 'str' type.",
            "Print type of 10.5.", "print(type(10.5))", "", "<class 'float'>"
        );

        // 6. Numbers
        long c6 = insertChapter(db, langId, "Numbers", "Integers and Floats", 6, 1);
        insertFullStep(db, c6,
            "There are three numeric types in Python: int, float, and complex. Int, or integer, is a whole number, positive or negative, without decimals, of unlimited length. Float, or \"floating point number\" is a number, positive or negative, containing one or more decimals.",
            "You can convert from one type to another with int(), float(), and complex() methods.",
            "int for whole numbers|float for decimals|e for scientific notation|Infinite precision for ints",
            "Python", "x = 1\ny = 2.8\nprint(int(y))", "2", "int() rounds down/truncates the float.",
            "Type of 5.0?", "int", "float", "str", "bool", "B", "Even if it ends in .0, it is a float.",
            "Print the result of int(3.9).", "Use int()", "", "3"
        );

        // 7. Casting
        long c7 = insertChapter(db, langId, "Casting", "Type conversion", 7, 1);
        insertFullStep(db, c7,
            "There may be times when you want to specify a type on to a variable. This can be done with casting. Python is an object-orientated language, and as such it uses classes to define data types, including its primitive types. Casting in python is therefore done using constructor functions.",
            "int(\"3\") will convert the string \"3\" to the integer 3.",
            "int() converts to integer|float() converts to decimal|str() converts to text|Safe conversion only",
            "Python", "x = str(3)\ny = int(\"10\")\nprint(y)", "10", "String \"10\" becomes integer 10.",
            "Result of str(5)?", "5", "\"5\"", "Error", "int", "B", "It converts the number to string text.",
            "Print float(10).", "Should be 10.0", "", "10.0"
        );

        // 8. Operators
        long c8 = insertChapter(db, langId, "Python Operators", "Arithmetic and Logic", 8, 1);
        insertFullStep(db, c8,
            "Operators are used to perform operations on variables and values. Python divides the operators into the following groups: Arithmetic (+, -, *, /, %, **, //), Assignment (=, +=), Comparison (==, !=, >, <), Logical (and, or, not), and Identity (is, is not).",
            "The '**' operator is used for exponentiation (power).",
            "// for floor division|** for power|'and' for logical AND|'is' for object identity",
            "Python", "print(2 ** 3)", "8", "2 to the power of 3 is 8.",
            "What is 10 // 3?", "3.33", "3", "4", "1", "B", "// rounds down to the nearest integer.",
            "Print result of 5 * 5.", "Use *", "", "25"
        );

        // 9. Lists
        long c9 = insertChapter(db, langId, "Lists", "Ordered collections", 9, 1);
        insertFullStep(db, c9,
            "Lists are used to store multiple items in a single variable. Lists are one of 4 built-in data types in Python used to store collections of data. List items are ordered, changeable, and allow duplicate values. List items are indexed, the first item has index [0], the second item has index [1] etc.",
            "Lists can contain different data types at once.",
            "[] define a list|Ordered and indexed|Mutable (changeable)|len() for size",
            "Python", "mylist = [\"apple\", \"banana\"]\nprint(mylist[0])", "apple", "Index 0 is the first item.",
            "How to add an item to a list 'L'?", "L.add()", "L.plus()", "L.append()", "L.push()", "C", "append() adds to the end.",
            "Create list [1, 2] print its length.", "Use len()", "", "2"
        );

        // 10. Tuples
        long c10 = insertChapter(db, langId, "Tuples", "Immutable lists", 10, 1);
        insertFullStep(db, c10,
            "Tuples are used to store multiple items in a single variable. A tuple is a collection which is ordered and unchangeable. Tuples are written with round brackets (). Tuple items are indexed, the first item has index [0], the second item has index [1] etc.",
            "Tuples are unchangeable, meaning that we cannot change, add or remove items.",
            "() define a tuple|Ordered and indexed|Immutable|Allows duplicates",
            "Python", "mytuple = (\"apple\", \"banana\")\nprint(mytuple[1])", "banana", "Accessing index 1.",
            "Can you change a tuple item?", "Yes", "No", "Only strings", "Only integers", "B", "Tuples are strictly immutable.",
            "Print length of tuple (1, 2, 3).", "Use len()", "", "3"
        );

        // 11. Dictionaries
        long c11 = insertChapter(db, langId, "Dictionaries", "Key-Value mapping", 11, 1);
        insertFullStep(db, c11,
            "Dictionaries are used to store data values in key:value pairs. A dictionary is a collection which is ordered, changeable and does not allow duplicates. Dictionaries are written with curly brackets, and have keys and values.",
            "As of Python version 3.7, dictionaries are ordered. In 3.6 and earlier, they are unordered.",
            "{} define a dictionary|key:value pairs|No duplicate keys|Access via ['key']",
            "Python", "car = {\"brand\": \"Ford\", \"year\": 1964}\nprint(car[\"brand\"])", "Ford", "Accessing value via its key.",
            "Symbol to separate key and value?", ".", ";", ":", "-", "C", "A colon separates the pair.",
            "Print value of 'a' in {'a': 100}.", "Access car['a']", "", "100"
        );

        // 12. Functions
        long c12 = insertChapter(db, langId, "Functions", "Reusable code blocks", 12, 1);
        insertFullStep(db, c12,
            "A function is a block of code which only runs when it is called. You can pass data, known as parameters, into a function. A function can return data as a result. In Python, a function is defined using the def keyword. To call a function, use the function name followed by parenthesis.",
            "Parameters are the variables listed inside the parentheses in the function definition.",
            "def keyword starts definition|Return via return|Called with ()|Indentation defines body",
            "Python", "def my_func():\n    print(\"Hi\")\n\nmy_func()", "Hi", "Define once, call anywhere.",
            "Keyword to create a function?", "func", "function", "def", "define", "B", "Python uses 'def' for definition.",
            "Function 'f' prints 'Ok', call it.", "Define then call", "", "Ok"
        );
    }

    private void insertFullStep(SQLiteDatabase db, long chId, 
                               String expText, String dyk, String keyPoints,
                               String langTag, String code, String out, String ctx,
                               String q, String a, String b, String c, String d, String correct, String qExp,
                               String inst, String hint, String starter, String testOut) {
        insertExplanation(db, chId, 1, expText, dyk, keyPoints);
        insertCode(db, chId, 2, langTag, code, out, ctx);
        insertQcm(db, chId, 3, q, a, b, c, d, correct, qExp);
        insertTest(db, chId, 4, inst, hint, starter, testOut);
    }

    private long insertLanguage(SQLiteDatabase db, String name, String desc) {
        ContentValues v = new ContentValues();
        v.put("name", name); v.put("description", desc);
        return db.insert("languages", null, v);
    }
    private long insertChapter(SQLiteDatabase db, long langId, String title, String desc, int order, int locked) {
        ContentValues v = new ContentValues();
        v.put("language_id", langId); v.put("title", title); v.put("description", desc); v.put("order_index", order); v.put("is_locked", locked);
        return db.insert(TABLE_NAME, null, v);
    }
    private void insertExplanation(SQLiteDatabase db, long chId, int order, String text, String dyk, String keyPoints) {
        ContentValues v = new ContentValues();
        v.put("chapter_id", chId); v.put("type", "explanation"); v.put("order_index", order);
        long sid = db.insert("steps", null, v);
        ContentValues ev = new ContentValues();
        ev.put("step_id", sid); ev.put("content_text", text); ev.put("did_you_know", dyk); ev.put("key_points", keyPoints);
        db.insert("explanation_content", null, ev);
    }
    private void insertCode(SQLiteDatabase db, long chId, int order, String tag, String code, String out, String context) {
        ContentValues v = new ContentValues();
        v.put("chapter_id", chId); v.put("type", "code"); v.put("order_index", order);
        long sid = db.insert("steps", null, v);
        ContentValues cv = new ContentValues();
        cv.put("step_id", sid); cv.put("language_tag", tag); cv.put("code_snippet", code); cv.put("expected_output", out); cv.put("context_text", context);
        db.insert("code_example_content", null, cv);
    }
    private void insertQcm(SQLiteDatabase db, long chId, int order, String q, String a, String b, String c, String d, String correct, String exp) {
        ContentValues v = new ContentValues();
        v.put("chapter_id", chId); v.put("type", "qcm"); v.put("order_index", order);
        long sid = db.insert("steps", null, v);
        ContentValues qv = new ContentValues();
        qv.put("step_id", sid); qv.put("question", q); qv.put("option_a", a); qv.put("option_b", b); qv.put("option_c", c); qv.put("option_d", d); qv.put("correct_option", correct); qv.put("explanation", exp);
        db.insert("qcm_content", null, qv);
    }
    private void insertTest(SQLiteDatabase db, long chId, int order, String inst, String hint, String starter, String out) {
        ContentValues v = new ContentValues();
        v.put("chapter_id", chId); v.put("type", "test"); v.put("order_index", order);
        long sid = db.insert("steps", null, v);
        ContentValues tv = new ContentValues();
        tv.put("step_id", sid); tv.put("instructions", inst); tv.put("hint", hint); tv.put("starter_code", starter); tv.put("expected_output", out); tv.put("test_cases", "[]");
        db.insert("test_content", null, tv);
    }

    public List<Language> getLanguages() {
        ArrayList<Language> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM languages", null);
        while (cursor.moveToNext()) { list.add(getLanguageFromCursor(cursor, db)); }
        cursor.close(); db.close();
        return list;
    }

    public Language getLanguageByName(String languageName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM languages WHERE name = ?", new String[]{languageName});
        Language language = null;
        if (cursor.moveToFirst()) { language = getLanguageFromCursor(cursor, db); }
        cursor.close(); db.close();
        return language;
    }

    private Language getLanguageFromCursor(Cursor cursor, SQLiteDatabase db) {
        int langId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        List<Language.Chapter> chapterList = new ArrayList<>();
        Cursor chapterCursor = db.rawQuery("SELECT * FROM chapters WHERE language_id = ? ORDER BY order_index", new String[]{String.valueOf(langId)});
        int total = 0, completed = 0;
        while (chapterCursor.moveToNext()) {
            int chId = chapterCursor.getInt(chapterCursor.getColumnIndexOrThrow("id"));
            String title = chapterCursor.getString(chapterCursor.getColumnIndexOrThrow("title"));
            String desc = chapterCursor.getString(chapterCursor.getColumnIndexOrThrow("description"));
            boolean locked = chapterCursor.getInt(chapterCursor.getColumnIndexOrThrow("is_locked")) == 1;
            chapterList.add(new Language.Chapter(chId, title, desc, locked, chapterCursor.getInt(chapterCursor.getColumnIndexOrThrow("order_index"))));
            total++; if (!locked) completed++;
        }
        chapterCursor.close();
        Language l = new Language(langId, name, description, (total > 0) ? (completed * 100 / total) : 0, completed, total);
        l.chapters = chapterList;
        return l;
    }

    public Map<String, String> getExplanationContent(int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> content = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT ec.* FROM explanation_content ec JOIN steps s ON ec.step_id = s.id WHERE s.chapter_id = ? AND s.type = 'explanation'", new String[]{String.valueOf(chapterId)});
        if (cursor.moveToFirst()) {
            content.put("text", cursor.getString(cursor.getColumnIndexOrThrow("content_text")));
            content.put("did_you_know", cursor.getString(cursor.getColumnIndexOrThrow("did_you_know")));
            content.put("key_points", cursor.getString(cursor.getColumnIndexOrThrow("key_points")));
        }
        cursor.close();
        return content;
    }

    public Map<String, String> getCodeContent(int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> content = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT cec.* FROM code_example_content cec JOIN steps s ON cec.step_id = s.id WHERE s.chapter_id = ? AND s.type = 'code'", new String[]{String.valueOf(chapterId)});
        if (cursor.moveToFirst()) {
            content.put("language", cursor.getString(cursor.getColumnIndexOrThrow("language_tag")));
            content.put("code", cursor.getString(cursor.getColumnIndexOrThrow("code_snippet")));
            content.put("output", cursor.getString(cursor.getColumnIndexOrThrow("expected_output")));
            content.put("context", cursor.getString(cursor.getColumnIndexOrThrow("context_text")));
        }
        cursor.close();
        return content;
    }

    public Map<String, String> getQcmContent(int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> content = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT qc.* FROM qcm_content qc JOIN steps s ON qc.step_id = s.id WHERE s.chapter_id = ? AND s.type = 'qcm'", new String[]{String.valueOf(chapterId)});
        if (cursor.moveToFirst()) {
            content.put("question", cursor.getString(cursor.getColumnIndexOrThrow("question")));
            content.put("option_a", cursor.getString(cursor.getColumnIndexOrThrow("option_a")));
            content.put("option_b", cursor.getString(cursor.getColumnIndexOrThrow("option_b")));
            content.put("option_c", cursor.getString(cursor.getColumnIndexOrThrow("option_c")));
            content.put("option_d", cursor.getString(cursor.getColumnIndexOrThrow("option_d")));
            content.put("correct", cursor.getString(cursor.getColumnIndexOrThrow("correct_option")));
            content.put("explanation", cursor.getString(cursor.getColumnIndexOrThrow("explanation")));
        }
        cursor.close();
        return content;
    }

    public Map<String, String> getTestContent(int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> content = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT tc.* FROM test_content tc JOIN steps s ON tc.step_id = s.id WHERE s.chapter_id = ? AND s.type = 'test'", new String[]{String.valueOf(chapterId)});
        if (cursor.moveToFirst()) {
            content.put("instructions", cursor.getString(cursor.getColumnIndexOrThrow("instructions")));
            content.put("hint", cursor.getString(cursor.getColumnIndexOrThrow("hint")));
            content.put("starter_code", cursor.getString(cursor.getColumnIndexOrThrow("starter_code")));
            content.put("expected_output", cursor.getString(cursor.getColumnIndexOrThrow("expected_output")));
        }
        cursor.close();
        return content;
    }

    public void unlockNextChapter(int currentChapterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT language_id, order_index FROM chapters WHERE id = ?", new String[]{String.valueOf(currentChapterId)});
        if (cursor.moveToFirst()) {
            int langId = cursor.getInt(0);
            int currentOrder = cursor.getInt(1);
            ContentValues values = new ContentValues();
            values.put("is_locked", 0);
            db.update("chapters", values, "language_id = ? AND order_index = ?", new String[]{String.valueOf(langId), String.valueOf(currentOrder + 1)});
        }
        cursor.close();
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS languages");
        db.execSQL("DROP TABLE IF EXISTS steps");
        db.execSQL("DROP TABLE IF EXISTS explanation_content");
        db.execSQL("DROP TABLE IF EXISTS code_example_content");
        db.execSQL("DROP TABLE IF EXISTS qcm_content");
        db.execSQL("DROP TABLE IF EXISTS test_content");
        onCreate(db);
    }
}

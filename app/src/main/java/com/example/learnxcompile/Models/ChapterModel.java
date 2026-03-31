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
    private static final String DATABASE_NAME = "learnXcompile_main_data";
    private static final int DATABASE_VERSION = 1;
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
            long javaId = insertLanguage(db, "Java", "A versatile, object-oriented language used for Android, enterprise, and cloud applications.");
            insertJavaChapters(db, javaId);

            long pythonId = insertLanguage(db, "Python", "A high-level language known for readability, used in AI, web development, and automation.");
            insertPythonChapters(db, pythonId);

            long cppId = insertLanguage(db, "C++", "A high-performance language used in games, systems, and performance-critical software.");
            insertCppChapters(db, cppId);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertJavaChapters(SQLiteDatabase db, long langId) {
        insertFullStep(db, insertChapter(db, langId, "Intro to Java", "JVM and Basic Structure", 1, 0),
                "Java is a high-level, class-based, object-oriented programming language. It follows the 'Write Once, Run Anywhere' (WORA) principle.", "Java was originally called 'Oak'.", "OOP|Platform Independent|Secure",
                "java", "public class Main {\n  public static void main(String[] args) {\n    System.out.println(\"Hello\");\n  }\n}", "Hello", "Entry point.",
                "What does JVM stand for?", "Java Variable Machine", "Java Virtual Machine", "Just Version Management", "None", "B", "JVM executes bytecode.",
                "Print 'Java'.", "Use System.out.println", "public class Main {\n  public static void main(String[] args) {\n    \n  }\n}", "Java");

        insertFullStep(db, insertChapter(db, langId, "Java Variables", "Storing Data", 2, 1),
                "Variables are containers for data. Common types: String, int, float, char, boolean.", "Java is strongly typed.", "int: integers|double: decimals",
                "java", "int age = 20;\nSystem.out.println(age);", "20", "Declaration.",
                "Type for whole numbers?", "float", "int", "char", "String", "B", "int stores integers.",
                "Declare int x=5.", "int x=5;", "public class Main {\n  public static void main(String[] args) {\n    \n  }\n}", "5");

        insertFullStep(db, insertChapter(db, langId, "Data Types", "Primitive vs Reference", 3, 1),
                "Data types specify the size and type of values. Primitive types include byte, short, int, long, float, double, boolean, and char.", "There are 8 primitive types.", "Primitives: byte, int, ...|Reference: String, Array",
                "java", "double myNum = 19.99;\nSystem.out.println(myNum);", "19.99", "Decimal type.",
                "Size of int in bytes?", "1", "2", "4", "8", "C", "Standard int is 4 bytes.",
                "Print double 1.5.", "System.out.println(1.5);", "public class Main {\n  public static void main(String[] args) {\n    \n  }\n}", "1.5");

        insertFullStep(db, insertChapter(db, langId, "Type Casting", "Widening vs Narrowing", 4, 1),
                "Casting is when you assign a value of one primitive data type to another type.", "Widening is automatic.", "Widening: small to large|Narrowing: large to small",
                "java", "int myInt = 9;\ndouble myDouble = myInt;\nSystem.out.println(myDouble);", "9.0", "Automatic casting.",
                "Manual casting name?", "Widening", "Narrowing", "Floating", "Boxing", "B", "Narrowing must be done manually.",
                "Cast 9.78 to int.", "(int) 9.78", "public class Main {\n  public static void main(String[] args) {\n    double d = 9.78;\n    int i = \n  }\n}", "9");

        insertFullStep(db, insertChapter(db, langId, "Operators", "Math and Logic", 5, 1), "Arithmetic, Assignment, Comparison, Logical.", "% is modulo.", "+ - * / %", "java", "int x = 10 + 5;", "15", "Math.", "10 % 3?", "1", "0", "3", "2", "A", "Remainder.", "Print 5 * 2.", "*", "public class Main {\n  public static void main(String[] args) {\n    \n  }\n}", "10");
        insertFullStep(db, insertChapter(db, langId, "Strings", "Text", 6, 1), "Strings are objects.", "Strings are immutable.", "length()|toUpperCase()", "java", "String s = \"Hi\";", "Hi", "Text.", "Find length?", "size()", "length()", "A", "B", "B", "length() method.", "Print 'A'.", "System.out.println(\"A\")", "", "A");
        insertFullStep(db, insertChapter(db, langId, "Math", "Functions", 7, 1), "Math class.", "Math.max(x,y).", "sqrt|random|abs", "java", "System.out.println(Math.max(5,10));", "10", "Max.", "sqrt(64)?", "8", "4", "A", "B", "A", "8*8=64.", "Print sqrt(25).", "Math.sqrt(25)", "", "5.0");
        insertFullStep(db, insertChapter(db, langId, "Booleans", "Logic", 8, 1), "True or False.", "Often used in conditions.", "boolean type", "java", "boolean isOk = true;", "true", "Logic.", "Value of 10 > 9?", "true", "false", "A", "B", "A", "10 is bigger.", "Print true.", "true", "", "true");
        insertFullStep(db, insertChapter(db, langId, "If...Else", "Decisions", 9, 1), "Conditional branching.", "else if for multi.", "if | else | else if", "java", "if(5>2) System.out.println(\"Yes\");", "Yes", "If block.", "Alternative keyword?", "elif", "else if", "A", "B", "B", "Java uses else if.", "If 1<2 print 'ok'.", "if(1<2) System.out.println(\"ok\");", "", "ok");
        insertFullStep(db, insertChapter(db, langId, "Switch", "Multi-branch", 10, 1), "Choosing one of many.", "Uses cases.", "switch | case | break", "java", "int day=1; switch(day){case 1: System.out.println(\"M\"); break;}", "M", "Switch.", "Stops execution?", "stop", "break", "A", "B", "B", "break exits switch.", "Switch on 1.", "switch(1)", "", "M");
        insertFullStep(db, insertChapter(db, langId, "While Loop", "Conditional Loop", 11, 1), "Repeats while true.", "Check condition first.", "while(condition){}", "java", "int i=0; while(i<2){System.out.print(i); i++;}", "01", "While loop.", "Runs at least once?", "while", "do-while", "A", "B", "B", "do-while check after.", "Loop twice.", "while(i<2)", "", "01");
        insertFullStep(db, insertChapter(db, langId, "For Loop", "Counted Loop", 12, 1), "Known iterations.", "Initializer, condition, increment.", "for(int i=0; i<n; i++)", "java", "for(int i=0; i<3; i++) System.out.print(i);", "012", "For loop.", "Middle part?", "Init", "Condition", "A", "B", "B", "Condition is checked.", "Print 0 1.", "for(int i=0; i<2; i++)", "", "01");
    }

    private void insertPythonChapters(SQLiteDatabase db, long langId) {
        insertFullStep(db, insertChapter(db, langId, "Intro to Python", "Easy Syntax", 1, 0), "High-level, interpreted language.", "Created by Guido van Rossum.", "Readable|Versatile", "python", "print(\"Hello\")", "Hello", "Basic output.", "When released?", "1985", "1991", "1995", "2000", "B", "Released in 1991.", "Print 'Py'.", "print('Py')", "", "Py");
        insertFullStep(db, insertChapter(db, langId, "Syntax", "Indentation", 2, 1), "Whitespace matters.", "No semicolons needed.", "Indentation is blocks", "python", "if True:\n  print(\"Hi\")", "Hi", "Code blocks.", "Comment start?", "//", "#", "A", "B", "B", "Uses # for comments.", "Correct indent.", "  print('A')", "if True:\n", "A");
        insertFullStep(db, insertChapter(db, langId, "Comments", "Documentation", 3, 1), "Explain code.", "Ignored by interpreter.", "# and \"\"\"", "python", "# This is a comment\nprint(\"Code\")", "Code", "Comments.", "Multi-line comment?", "'''", "///", "A", "B", "A", "Triple quotes.", "Add a comment.", "# comment", "", "Code");
        insertFullStep(db, insertChapter(db, langId, "Variables", "Dynamic Typing", 4, 1), "No declaration needed.", "Types can change.", "x = 5", "python", "x = 5\nx = \"A\"\nprint(x)", "A", "Variable reuse.", "Invalid name?", "myVar", "2var", "A", "B", "B", "Can't start with number.", "Assign x=10.", "x = 10", "", "10");
        insertFullStep(db, insertChapter(db, langId, "Data Types", "Built-ins", 5, 1), "str, int, float, list, etc.", "type() to check.", "Dynamic types", "python", "x = 5\nprint(type(x))", "<class 'int'>", "Checking type.", "Type of 'Hello'?", "int", "str", "A", "B", "B", "String is str.", "Print type of 1.5.", "type(1.5)", "", "<class 'float'>");
        insertFullStep(db, insertChapter(db, langId, "Numbers", "Int and Float", 6, 1), "Numeric types.", "Complex types supported.", "int | float | complex", "python", "x = 1\ny = 2.8\nprint(x)", "1", "Numbers.", "Is 1.0 float?", "Yes", "No", "A", "B", "A", "Decimal point.", "Print 5 + 5.", "5 + 5", "", "10");
        insertFullStep(db, insertChapter(db, langId, "Casting", "Conversion", 7, 1), "Change types.", "int(), float(), str().", "Constructor functions", "python", "x = int(2.8)\nprint(x)", "2", "Float to int.", "str(123)?", "'123'", "123", "A", "B", "A", "Converts to string.", "Cast '3' to int.", "int('3')", "", "3");
        insertFullStep(db, insertChapter(db, langId, "Strings", "Text Arrays", 8, 1), "Strings are arrays.", "Single or double quotes.", "len() | upper()", "python", "a = \"Hello\"\nprint(a[1])", "e", "Indexing.", "Find length?", "length()", "len()", "A", "B", "B", "len() function.", "Print 'H'.", "a[0]", "a='Hi'\n", "H");
        insertFullStep(db, insertChapter(db, langId, "Booleans", "True/False", 9, 1), "Boolean values.", "bool() evaluation.", "True | False", "python", "print(10 > 9)", "True", "Evaluation.", "Value of bool(0)?", "True", "False", "A", "B", "B", "0 is False.", "Print False.", "False", "", "False");
        insertFullStep(db, insertChapter(db, langId, "Operators", "Math Logic", 10, 1), "Arithmetic etc.", "// is floor division.", "+ - * / // %", "python", "print(10 // 3)", "3", "Floor division.", "Exponent operator?", "**", "^", "A", "B", "A", "** is power.", "Print 2 ** 3.", "2**3", "", "8");
        insertFullStep(db, insertChapter(db, langId, "Lists", "Ordered", 11, 1), "Collections.", "Changeable.", "[item1, item2]", "python", "L = [\"a\", \"b\"]\nprint(L[0])", "a", "Lists.", "Add to end?", "add()", "append()", "A", "B", "B", "append() adds.", "Print second item.", "L[1]", "L=[1,2]\n", "2");
        insertFullStep(db, insertChapter(db, langId, "Tuples", "Immutable", 12, 1), "Ordered collections.", "Cannot change.", "(item1, item2)", "python", "T = (\"a\", \"b\")\nprint(T[0])", "a", "Tuples.", "Can you change tuple?", "Yes", "No", "A", "B", "B", "Tuples are immutable.", "Define tuple (1,2).", "(1,2)", "", "(1, 2)");
    }

    private void insertCppChapters(SQLiteDatabase db, long langId) {
        insertFullStep(db, insertChapter(db, langId, "Intro to C++", "Performance", 1, 0), "Extension of C.", "Cross-platform.", "Fast | Low-level", "cpp", "#include <iostream>\nint main() {\n  std::cout << \"Hi\";\n  return 0;\n}", "Hi", "Hello World.", "Creator?", "Gosling", "Stroustrup", "A", "B", "B", "Bjarne Stroustrup.", "Print 'C++'.", "std::cout << \"C++\";", "", "C++");
        insertFullStep(db, insertChapter(db, langId, "Syntax", "Structure", 2, 1), "Uses semicolons.", "Main is entry.", "Blocks with {}", "cpp", "int x=5; std::cout << x;", "5", "Basic code.", "End statement?", ".", ";", "A", "B", "B", "Semicolons required.", "Add semicolon.", "int x=1;", "", "1");
        insertFullStep(db, insertChapter(db, langId, "Output", "cout", 3, 1), "Character output.", "Uses <<.", "std::cout", "cpp", "std::cout << \"A\" << \"B\";", "AB", "Output stream.", "Operator for cout?", "<<", ">>", "A", "B", "A", "Insertion operator.", "Print 'OK'.", "cout << \"OK\";", "", "OK");
        insertFullStep(db, insertChapter(db, langId, "Comments", "Notes", 4, 1), "Single and multi line.", "Ignored by compiler.", "// and /* */", "cpp", "// comment\nint x=1;", "1", "Notes.", "Multi-line start?", "//", "/*", "A", "B", "B", "Block comments.", "Add comment.", "// comment", "", "1");
        insertFullStep(db, insertChapter(db, langId, "Variables", "Types", 5, 1), "Containers.", "Statically typed.", "int | double | char", "cpp", "int x = 10; std::cout << x;", "10", "Variables.", "Type for decimal?", "int", "double", "A", "B", "B", "double stores floats.", "Declare int y=2.", "int y=2;", "", "2");
        insertFullStep(db, insertChapter(db, langId, "User Input", "cin", 6, 1), "Character input.", "Uses >>.", "std::cin", "cpp", "int x; std::cin >> x; // assume 5", "5", "Input stream.", "Operator for cin?", "<<", ">>", "A", "B", "B", "Extraction operator.", "Read x.", "cin >> x;", "", "5");
        insertFullStep(db, insertChapter(db, langId, "Data Types", "Memory", 7, 1), "int, float, char, etc.", "bool is 1 or 0.", "Numeric | Boolean", "cpp", "bool b = true; std::cout << b;", "1", "Types.", "Size of char?", "1 byte", "4 bytes", "A", "B", "A", "char is 1 byte.", "Print 1.5f.", "1.5f", "", "1.5");
        insertFullStep(db, insertChapter(db, langId, "Operators", "Math", 8, 1), "Arithmetic + - * / %.", "Increment ++.", "Operators", "cpp", "int x = 10 % 3; std::cout << x;", "1", "Math.", "5++ value?", "6", "5", "A", "B", "A", "Adds one.", "Print 10/2.", "10/2", "", "5");
        insertFullStep(db, insertChapter(db, langId, "Strings", "Text", 9, 1), "Part of <string>.", "Concatenate with +.", "std::string", "cpp", "#include <string>\nstd::string s = \"Hi\";", "Hi", "Text.", "Find length?", "size()", "len()", "A", "B", "A", "size() or length().", "Print 'A'.", "cout << \"A\";", "", "A");
        insertFullStep(db, insertChapter(db, langId, "Math", "Header", 10, 1), "<cmath> library.", "sqrt, pow, abs.", "Math functions", "cpp", "#include <cmath>\nstd::cout << sqrt(9);", "3", "Math.", "pow(2,3)?", "8", "6", "A", "B", "A", "2 cubed is 8.", "Print pow(2,2).", "pow(2,2)", "", "4");
        insertFullStep(db, insertChapter(db, langId, "Booleans", "Logic", 11, 1), "true (1), false (0).", "bool type.", "Logic values", "cpp", "bool isOk = true; std::cout << isOk;", "1", "Logic.", "Value of false?", "1", "0", "A", "B", "B", "false is 0.", "Print true.", "true", "", "1");
        insertFullStep(db, insertChapter(db, langId, "If...Else", "Branching", 12, 1), "if, else if, else.", "Logical flow.", "Control structures", "cpp", "if(5>2) std::cout << \"Y\";", "Y", "Decisions.", "Else if keyword?", "elif", "else if", "A", "B", "B", "Same as Java.", "If 1<2 print 'ok'.", "if(1<2) cout << \"ok\";", "", "ok");
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
        cursor.close();
        return list;
    }

    public Language getLanguageByName(String languageName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM languages WHERE name = ?", new String[]{languageName});
        Language language = null;
        if (cursor.moveToFirst()) { language = getLanguageFromCursor(cursor, db); }
        cursor.close();
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

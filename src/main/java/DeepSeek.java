public class DeepSeek {
    public static String promptStart = "Do the following exersice:\n";
    public static String promptEnd = """
\n\nWrite every id of the object you need to click. Write the answer in the following format:
Result1: <id> | <Option index>
Result2: <id> | action: type = <text to type>
Result3: <id> | action: click
...
            """;

    public static String promptFromEx(String HtmlEx) {
        return promptStart + HtmlEx + promptEnd;
    }
}

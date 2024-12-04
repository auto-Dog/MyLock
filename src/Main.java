//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        multiProcessDemo R1 = new multiProcessDemo( "Thread-1");
        R1.start();

        multiProcessDemo R2 = new multiProcessDemo( "Thread-2");
        R2.start();
    }
}
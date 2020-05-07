# letter_side_bar 安卓侧边字母sidebar
    ```
        //在布局文件中布局一个TextView和LetterSideBar
        //在onCreate方法中找到对应的view
        TextView textView = findViewById(R.id.textView);
        LetterSideBar letterSideBar = findViewById(R.id.letterSideBar);
        String[] letter = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        //设置letterSideBar要显示的字符，默认26个大写字母加#
        letterSideBar.setLetters(letter);
        //设置正在触摸字母的回调监听
        letterSideBar.setOnTouchingTextListener((text) -> {
            if (text == null) {
                textView.setVisibility(View.GONE);
                textView.setText("");
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            }
        });
    ```
![侧边栏](C:\Users\Daem\Desktop\demo.gif "demo")

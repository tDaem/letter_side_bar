# letter_side_bar 侧边字母sidebar
    ```
        <!-- 布局文件，自定义了四个属性，分别为正常的文字大小、颜色 和 高亮的文字大小、颜色 -->
        <com.hkdg.lettersidebar.LetterSideBar
            android:id="@+id/letterSideBar"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:paddingLeft="8dp"
            android:paddingTop="4dp"
            android:paddingRight="12dp"
            android:paddingBottom="4dp"
            app:highLightTextColor="@android:color/holo_red_dark"
            app:highLightTextSize="21sp"
            app:normalTextColor="@color/colorPrimary"
            app:normalTextSize="17sp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent">
        </com.hkdg.lettersidebar.LetterSideBar>
    ```
    ```
        //在布局文件中布局一个TextView和LetterSideBar
        //在onCreate方法中找到对应的view
        TextView textView = findViewById(R.id.textView);
        LetterSideBar letterSideBar = findViewById(R.id.letterSideBar);
        //设置letterSideBar要显示的字符，默认26个大写字母加#
        //String[] letter = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        //letterSideBar.setLetters(letter);
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
![侧边栏](https://github.com/tDaem/letter_side_bar/blob/master/lettersidebar/src/main/res/drawable/demo.gif "demo")

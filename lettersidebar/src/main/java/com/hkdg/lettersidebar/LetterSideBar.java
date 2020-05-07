package com.hkdg.lettersidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterSideBar extends View {

    //定义26个字母
    private static final String[] mLetters = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"
    };

    private String[] letters;

    /**
     * 普通字母画笔
     */
    private Paint mPaint;
    /**
     * 普通字母大小
     */
    private float mNormalTextSize = 17;
    /**
     * 普通字母颜色
     */
    private int mNormalTextColor = Color.BLUE;
    /**
     * 当前触摸的字母
     */
    private String currentTouchLetter;
    /**
     * 高亮字母画笔
     */
    private Paint mHighLightPaint;
    /**
     * 高亮字母大小
     */
    private float mHighLightTextSize = 24;
    /**
     * 高亮字母颜色
     */
    private int mHighLightTextColor = Color.RED;

    /**
     * 当前触摸文字监听
     */
    private OnTouchingTextListener onTouchingTextListener;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        letters = mLetters;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        mNormalTextColor = typedArray.getColor(R.styleable.LetterSideBar_normalTextColor, mNormalTextColor);
        mNormalTextSize = typedArray.getDimension(R.styleable.LetterSideBar_normalTextSize, sp2px(mNormalTextSize));

        mHighLightTextColor = typedArray.getColor(R.styleable.LetterSideBar_highLightTextColor, mHighLightTextColor);
        mHighLightTextSize = typedArray.getDimension(R.styleable.LetterSideBar_highLightTextSize, sp2px(mHighLightTextSize));
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mNormalTextSize);
        mPaint.setColor(mNormalTextColor);

        mHighLightPaint = new Paint();
        mHighLightPaint.setAntiAlias(true);
        mHighLightPaint.setTextSize(mHighLightTextSize);
        mHighLightPaint.setColor(mHighLightTextColor);
    }

    private float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算指定宽度 = 左右的padding + 字母的宽度
        int textWidth = 0;
        for (int i = 0; i < letters.length; i++) {
            int temp = (int) mPaint.measureText(letters[i]);
            if (temp > textWidth)
                textWidth = temp;
        }
        int width = getPaddingLeft() + getPaddingRight() + textWidth;

        //告诉可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画26个字母
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            int baseLine = dy + letterCenterY;
            if (letters[i].equals(currentTouchLetter)) {
                //知道每个字母的中心位置
                int x = (int) (getWidth() - mHighLightPaint.measureText(letters[i]) + getPaddingLeft() - getPaddingRight()) / 2;
                canvas.drawText(letters[i], x, baseLine, mHighLightPaint);
            } else {
                //知道每个字母的中心位置
                int x = (int) (getWidth() - mPaint.measureText(letters[i]) + getPaddingLeft() - getPaddingRight()) / 2;
                canvas.drawText(letters[i], x, baseLine, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float currentMoveY = event.getY();
                //处理未触摸到字母
                if (currentMoveY < getPaddingTop() || currentMoveY > getHeight() - getPaddingBottom())
                    break;
                //字母的高度
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
                //当前按下字母的索引
                int currentPosition = (int) ((currentMoveY - getPaddingTop() + getPaddingBottom()) / itemHeight);
                currentTouchLetter = currentPosition < letters.length ? letters[currentPosition] : null;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                currentTouchLetter = null;
                invalidate();
                break;
        }
        if (onTouchingTextListener != null) {
            onTouchingTextListener.onTouchingText(currentTouchLetter);
        }
        return true;
    }

    public void setOnTouchingTextListener(OnTouchingTextListener onTouchingTextListener) {
        this.onTouchingTextListener = onTouchingTextListener;
    }

    public void setLetters(String[] letters) {
        this.letters = letters;
    }

    public interface OnTouchingTextListener {
        void onTouchingText(@Nullable String text);
    }
    
}

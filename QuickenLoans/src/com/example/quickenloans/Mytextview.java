package com.example.quickenloans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

public class Mytextview extends TextView{

		LinearGradient mLinearGradient;
	    Matrix mGradientMatrix;
	    Paint mPaint;
	    int mViewWidth = 0;
	    int mTranslate = 0;
	    boolean mAnimating = true;
	    Handler handle;
	
	public Mytextview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public Mytextview(Context context) {
		super(context);
	}
	
	 @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        super.onSizeChanged(w, h, oldw, oldh);
	        if (mViewWidth == 0) {
	            mViewWidth = getMeasuredWidth();
	            if (mViewWidth > 0) {
	                mPaint = getPaint();
	                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,  
	                        new int[] { Color.BLACK,Color.WHITE, Color.BLACK },  
	                        new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);  
	                mPaint.setShader(mLinearGradient);
	                mGradientMatrix = new Matrix();
	            }
	        }
	    }

	 @Override  
	    protected void onDraw(Canvas canvas) {  
	        super.onDraw(canvas);  
	        if (mAnimating && mGradientMatrix != null) {  
	            mTranslate += mViewWidth / 10;  
	            if (mTranslate > 2 * mViewWidth) {  
	                mTranslate = -mViewWidth;  
	            }  
	            mGradientMatrix.setTranslate(mTranslate, 0);  
	            mLinearGradient.setLocalMatrix(mGradientMatrix);  
	            postInvalidateDelayed(50);  
	        }  
	    }  

}

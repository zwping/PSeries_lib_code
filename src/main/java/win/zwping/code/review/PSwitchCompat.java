package win.zwping.code.review;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class PSwitchCompat extends SwitchCompat {
    public PSwitchCompat(Context context) {
        this(context,null);
    }

    public PSwitchCompat(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


//        post(new Runnable() {
//            @Override
//            public void run() {
//
//                // thumb color
//                int thumbColor = Color.RED;
//
//                // trackColor
//                int trackColor = Color.DKGRAY;
//
//                // set the thumb color
//                DrawableCompat.setTintList(getThumbDrawable(), new ColorStateList(
//                        new int[][]{
//                                new int[]{android.R.attr.state_checked},
//                                new int[]{}
//                        },
//                        new int[]{
//                                thumbColor,§
//                                trackColor
//                        }));
//
//                // set the track color
//                DrawableCompat.setTintList(getTrackDrawable(), new ColorStateList(
//                        new int[][]{
//                                new int[]{android.R.attr.state_checked},
//                                new int[]{}
//                        },
//                        new int[]{
//                                0x4DFF6633,
//                                0x4d2f2f2f
//                        }));
//
//            }
//        });
    }
}

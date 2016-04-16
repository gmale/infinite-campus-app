package com.kevingorham.infinitecampus.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @since 4/16/16.
 */
public class Toaster {
	public static void show(Context context, String format, Object... args) {
		String message = String.format(format, args);
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}

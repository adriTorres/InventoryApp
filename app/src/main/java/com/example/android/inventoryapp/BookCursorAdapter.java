package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.BookContract;
import com.example.android.inventoryapp.data.BookContract.BookEntry;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data to the given
     * list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView authorTextView = (TextView) view.findViewById(R.id.author);
        TextView priceTextView = (TextView) view.findViewById(R.id.price_list_element);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity_list_element);
        Button saleButton = (Button) view.findViewById(R.id.button_sale);


        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        String bookAuthor = cursor.getString(authorColumnIndex);
        Float bookPrice = cursor.getFloat(priceColumnIndex);
        final Integer bookQuantity = cursor.getInt(quantityColumnIndex);

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(bookName);
        authorTextView.setText(bookAuthor);
        priceTextView.setText(context.getResources().getString(R.string.price_list, bookPrice));
        quantityTextView.setText(context.getResources().getString(R.string.quantity_list, bookQuantity));

        final Uri uri = ContentUris.withAppendedId(BookEntry.CONTENT_URI,
                cursor.getInt(cursor.getColumnIndexOrThrow(BookEntry._ID)));

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int quantity = bookQuantity;

                if (quantity > 0) {
                    int result = quantity - 1;

                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, result);
                    context.getContentResolver().update(uri, values, null, null);

                    quantityTextView.setText(context.getResources().getString(R.string.quantity_list, quantity));
                }
            }
        });
    }
}

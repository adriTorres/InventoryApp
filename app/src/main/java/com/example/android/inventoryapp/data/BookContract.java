package com.example.android.inventoryapp.data;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * Name for the entire content provider. Same as the name of the package of the app.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path to access all the books in the database
     */
    public static final String PATH_BOOKS = "books";

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        /** The content URI to access the book data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of books.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /** Name of database table for books */
        public final static String TABLE_NAME = "books";

        /**
         * Unique ID number for the book (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_NAME ="name";

        /**
         * Author of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_AUTHOR ="author";

        /**
         * Price of the pet.
         *
         * Type: REAL
         */
        public final static String COLUMN_BOOK_PRICE = "price";

        /**
         * Quantity of books
         *
         * From 1 to 100
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        /**
         * Supplier name of the books.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";

        /**
         * Supplier phone of the books.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";

    }
}

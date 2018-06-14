package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

import java.util.ArrayList;
import java.util.List;

public class EditorActivity  extends AppCompatActivity {

    /** EditText field to enter the book's name */
    private EditText mNameEditText;

    /** EditText field to enter the book's author */
    private EditText mAuthorEditText;

    /** EditText field to enter the book's price */
    private EditText mPriceEditText;

    /** EditText field to enter the book supplier's name */
    private EditText mSuppNameEditText;

    /** EditText field to enter the book supplier's phone */
    private EditText mSuppPhoneEditText;

    /** EditText field to enter the book's quantity */
    private Spinner mQuantitySpinner;

    /** Default Quantity */
    private int mQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_book_name);
        mAuthorEditText = (EditText) findViewById(R.id.edit_book_author);
        mPriceEditText = (EditText) findViewById(R.id.edit_book_price);
        mSuppNameEditText = (EditText) findViewById(R.id.edit_supp_name);
        mSuppPhoneEditText = (EditText) findViewById(R.id.edit_supp_phone);
        mQuantitySpinner = (Spinner) findViewById(R.id.spinner_quantity);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the quantity of books.
     */
    private void setupSpinner() {

        // Create adapter for spinner. Options from minimun of 1 to maximun of 100
        List quantity = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            quantity.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, quantity);

        // Specify dropdown layout style - simple list view with 1 item per line
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        // Apply the adapter to the spinner
        mQuantitySpinner.setAdapter(spinnerArrayAdapter);

        // Set the integer mSelected to the constant values
        mQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                mQuantity = Integer.parseInt(selection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mQuantity = 1;
            }
        });
    }

    public void insertPet(View view){
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String authorString = mAuthorEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        double price = Double.parseDouble(priceString);
        String suppNameString = mSuppNameEditText.getText().toString().trim();
        String suppPhoneString = mSuppPhoneEditText.getText().toString().trim();

        // Create database helper
        BookDbHelper mDbHelper = new BookDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and book attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, nameString);
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, authorString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, mQuantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, suppNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, suppPhoneString);

        // Insert a new row for a book in the database, returning the ID of that new row.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}

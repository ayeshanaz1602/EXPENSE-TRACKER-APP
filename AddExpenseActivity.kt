class AddExpenseActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var expenseDao: ExpenseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        db = AppDatabase.getInstance(this)
        expenseDao = db.expenseDao()

        saveButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val category = categorySpinner.selectedItem.toString()
            val note = noteEditText.text.toString()
            val expense = Expense(amount = amount, category = category, note = note, timestamp = System.currentTimeMillis())

            lifecycleScope.launch {
                expenseDao.insert(expense)
                finish()
            }
        }
    }
}

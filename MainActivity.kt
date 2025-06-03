class MainActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var expenseDao: ExpenseDao
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)
        expenseDao = db.expenseDao()

        adapter = ExpenseAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadExpenses()
        addButton.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
    }

    private fun loadExpenses() {
        lifecycleScope.launch {
            val expenses = expenseDao.getAll()
            adapter.submitList(expenses)
            updateChart(expenses)
        }
    }

    private fun updateChart(expenses: List<Expense>) {
        val groupByCategory = expenses.groupBy { it.category }
        val entries = groupByCategory.map {
            PieEntry(it.value.sumOf { e -> e.amount }.toFloat(), it.key)
        }

        val dataSet = PieDataSet(entries, "Categories")
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()
    }
}
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*

lateinit var pieChart: PieChart
pieChart = findViewById(R.id.pieChart)

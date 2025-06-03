@Dao
interface ExpenseDao {
    @Insert suspend fun insert(expense: Expense)
    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    suspend fun getAll(): List<Expense>
    @Delete suspend fun delete(expense: Expense)
}

package vcmsa.ci.radio

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var RadioInstallDoor: RadioButton
    private lateinit var RadioBuildShelf: RadioButton
    private lateinit var RadioAssembleFurniture: RadioButton
    private lateinit var CheckWood: CheckBox
    private lateinit var CheckNails: CheckBox
    private lateinit var CheckHinges: CheckBox
    private lateinit var etHours: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    private val materialCosts = mapOf(
        "Wood" to 500,
        "Nails" to 20,
        "Hinges" to 30
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        RadioInstallDoor = findViewById(R.id.RadioInstallDoor)
        RadioBuildShelf = findViewById(R.id.RadioBuildShelf)
        RadioAssembleFurniture = findViewById(R.id.RadioAssembleFurniture)
        CheckWood = findViewById(R.id.CheckWood)
        CheckNails = findViewById(R.id.CheckNails)
        CheckHinges = findViewById(R.id.CheckHinges)
        etHours = findViewById(R.id.etHours)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvResult = findViewById(R.id.tvResult)

        // Button click listener
        btnCalculate.setOnClickListener {
            calculateTotalCost()
        }
    }

    private fun calculateTotalCost() {
        val hoursText = etHours.text.toString()

        // Validate hours
        if (hoursText.isBlank()) {
            showToast("Please enter the number of hours.")
            return
        }

        val hours = hoursText.toDoubleOrNull()
        if (hours == null || hours <= 0) {
            showToast("Enter a valid number of hours.")
            return
        }

        // Check task selection
        if (!RadioInstallDoor.isChecked && !RadioBuildShelf.isChecked && !RadioAssembleFurniture.isChecked) {
            showToast("Please select a task.")
            return
        }

        // Process selected materials
        var materialCost = 0
        val selectedMaterials = listOf(
            Pair(CheckWood, "Wood"),
            Pair(CheckNails, "Nails"),
            Pair(CheckHinges, "Hinges")
        )

        var anyMaterialSelected = false
        for ((checkbox, name) in selectedMaterials) {
            if (checkbox.isChecked) {
                materialCost += materialCosts[name] ?: 0
                anyMaterialSelected = true
            }
        }

        if (!anyMaterialSelected) {
            showToast("Please select at least one material.")
            return
        }

        // Calculate total
        val totalCost = materialCost * hours
        tvResult.text = String.format("Total Estimated Cost: R%.2f", totalCost)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

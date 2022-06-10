package com.ahimsarijalu.investin.ui.settings.edit_financial_data

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivityEditFinancialDataBinding
import com.ahimsarijalu.investin.ui.auth.SignupActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class EditFinancialDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditFinancialDataBinding
    private lateinit var interpreter: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditFinancialDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.edit_financial_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAction()
    }

    private fun setupAction() {
        binding.saveFinanceDataBtn.setOnClickListener {
            runModel()
        }
    }

    private fun runModel() {

        val conditions = CustomModelDownloadConditions.Builder().build()
        FirebaseModelDownloader.getInstance()
            .getModel(
                "revenue-growth", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                conditions
            )
            .addOnSuccessListener { model: CustomModel? ->
                val modelFile = model?.file
                if (modelFile != null) {
                    interpreter = Interpreter(modelFile)
                    val inputs: FloatArray = floatArrayOf(
                        binding.gpgTextField.editText?.text.toString().toFloat(),
                        binding.gmTextField.editText?.text.toString().toFloat(),
                        binding.intangibleToTotalAssetsTextField.editText?.text.toString()
                            .toFloat(),
                        binding.netIncomeTextField.editText?.text.toString().toFloat(),
                        binding.assetsTurnoverTextField.editText?.text.toString().toFloat(),
                        binding.revenueTextField.editText?.text.toString().toFloat(),
                        binding.interestExpenseTextField.editText?.text.toString().toFloat(),
                        binding.debtToAssetsTextField.editText?.text.toString().toFloat(),
                        binding.rndTextField.editText?.text.toString().toFloat(),
                        binding.sGnATextField.editText?.text.toString().toFloat()
                    )

                    val bufferSize = 1000 * java.lang.Float.SIZE / java.lang.Byte.SIZE
                    val output =
                        ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
                    interpreter.run(inputs, output)

                    val revenueGrowth = output.getFloat(0)
                    updateToFirestore(revenueGrowth)
                }
            }
    }

    private fun updateToFirestore(revenueGrowth: Float) {
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val data = mapOf(
            "revenueGrowth" to revenueGrowth
        )

        if (user != null) {
            db.collection("users")
                .document(user.uid)
                .update(data)
                .addOnFailureListener { e ->
                    Log.w(
                        SignupActivity.TAG,
                        "Error writing document",
                        e
                    )
                }
                .addOnCompleteListener {
                    Toast.makeText(
                        this, "Financial Data updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
package com.example.kotlinretrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        get_repo_data.setOnClickListener {
            val name2 = user_name.text.toString()
            Log.e("User name", name2)
            getCurrentData(name2)
        }
    }

    private fun getCurrentData(name2: String) {
        Log.e("User name", name2)
        GitService.create().getGivenUsersRepos(name2).enqueue(object : Callback<GitResponse> {
            override fun onResponse(call: Call<GitResponse>, response: Response<GitResponse>) {
                if (response.code() == 200) {
                    val gitResponse = response.body()!!

                    var stringBuilder = "Total Count: " +
                            gitResponse.totalCount +
                            "\n" +
                            "Is incomplete? " +
                            gitResponse.incompleteResults +
                            "\n"

                    val userList = gitResponse.items
                    userList?.forEach {
//                        Log.v("Name", it?.login)
                        val name = it?.login
                        val score = it?.score
                        stringBuilder += "Name: $name,  Score: $score \n"
                    }
                    Log.e("Received Data", stringBuilder)

                    repo_details!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                try {
                    print("Error in enqueue")
                } catch (e: Exception) {
                    Log.e("Error", "Error in enqueue")
                }
            }
        })
    }

    companion object {
        var name = "robin"
    }
}

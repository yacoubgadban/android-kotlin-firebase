package com.yg.firebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yg.firebase.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    val userList=ArrayList<User>()
    lateinit var userAdapter: UserAdapter

    //binding
    private lateinit var binding: ActivityMainBinding

    //database
    private val database: FirebaseDatabase=FirebaseDatabase.getInstance()
    private val reference:DatabaseReference=database.reference.child("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)



        binding.floatingActionButton.setOnClickListener{
            val intent= Intent(this,CreateUserActivity::class.java)
            startActivity(intent)

        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id=userAdapter.getUserId(viewHolder.adapterPosition)
                val name=userAdapter.getUserName(viewHolder.adapterPosition)
                reference.child(id).removeValue()
               Toast.makeText(applicationContext,"$name deleted !",Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.recycleView)


        retrieveDataFromDatabase()
        deleteAllUsers()
        logout()
    }






    private fun retrieveDataFromDatabase(){
        reference.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (item in snapshot.children){
                    var user =item.getValue(User::class.java)
                    userList.add(user!!)
                }
                userAdapter=UserAdapter(this@MainActivity,userList)
                binding.recycleView.layoutManager=LinearLayoutManager(this@MainActivity)
                binding.recycleView.adapter=userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_delete_all,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.deleteAll){
            showDialogMessage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogMessage (){

        val dialogMessage=AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Users")
        dialogMessage.setMessage("If you click yes , all users will be deleted,"+
        "If you want to delete specific user , you can swipe the user right or left")
        dialogMessage.setNegativeButton("Cancel",DialogInterface.OnClickListener{dialogInterface,i->

            dialogInterface.cancel()
        })

        dialogMessage.setPositiveButton("Yes",DialogInterface.OnClickListener{dialogInterface,i->

            reference.removeValue().addOnCompleteListener{task ->
                if (task.isSuccessful){
                    userAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext,"All user have been deleted",Toast.LENGTH_SHORT).show()
                }
            }
        })
        dialogMessage.create().show()
    }

    private fun deleteAllUsers(){
        binding.deleteall.setOnClickListener {
            showDialogMessage()
        }
    }

    private fun logout(){
        binding.btnLogout.setOnClickListener {
           FirebaseAuth.getInstance().signOut()
            val intent=Intent(this,AppLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
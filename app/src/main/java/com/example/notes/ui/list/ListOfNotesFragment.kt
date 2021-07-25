package com.example.notes.ui.list

import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notes.App
import com.example.notes.ListItemClickListener
import com.example.notes.R
import com.example.notes.RegisterViewListener
import com.example.notes.data.DataChangedListener
import com.example.notes.data.INotesSource
import com.example.notes.data.firebase.NoteSourceFirebase
import com.example.notes.data.firebase.NoteSourceResponse
import com.example.notes.ui.dialogs.DeleteDialog
import com.example.notes.ui.dialogs.EditNoteDialogFragment

class ListOfNotesFragment : Fragment() {
    private lateinit var adapter: ListOfNotesAdapter
    private lateinit var noteSource: INotesSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.list_of_notes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        initRecyclerView(recyclerView)
        val animator = DefaultItemAnimator()
        animator.addDuration = 1000
        animator.removeDuration = 1000
        recyclerView.itemAnimator = animator
        val refresh: SwipeRefreshLayout = view.findViewById(R.id.refresh)
        noteSource = NoteSourceFirebase().init(object : NoteSourceResponse{
            override fun initialized(notesSource: INotesSource) {
                adapter.notifyDataSetChanged()
            }
        })
        refresh.setOnRefreshListener {
            (noteSource as NoteSourceFirebase).init(object : NoteSourceResponse{
                override fun initialized(notesSource: INotesSource) {
                    adapter.notifyDataSetChanged()
                    refresh.isRefreshing = false
                }
            })
        }
        (requireActivity().application as App).notesSource = noteSource
        adapter.setDataSource(noteSource)
        noteSource.setOnChangedListener(object : DataChangedListener {
            override fun onDataAdd(position: Int) {
                adapter.notifyItemInserted(position)
            }

            override fun onDataUpdate(position: Int) {
                adapter.notifyItemChanged(position)
            }

            override fun onDataDelete(position: Int) {
                adapter.notifyItemRemoved(position)
            }
        })
        return view
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        setHasOptionsMenu(true)
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        adapter = ListOfNotesAdapter()
        adapter.setRegisterViewListener(object : RegisterViewListener {
            override fun registerView(view: View) {
                registerForContextMenu(view)
            }
        })
        adapter.setOnItemClickListener(object : ListItemClickListener {
            override fun onClick(view: View, position: Int) {
                val fragment: DialogFragment = EditNoteDialogFragment()
                val bundle = Bundle()
                bundle.putInt(INDEX_KEY, position)
                fragment.arguments = bundle
                fragment.show(parentFragmentManager, TAG_EDIT_NOTE)
            }
        })
        recyclerView.adapter = adapter
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        menu.add(0, MENU_CHANGE_COLOR, 0, R.string.change_color)
        menu.add(0, MENU_DELETE, 1, R.string.delete)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_CHANGE_COLOR -> {
                val note = noteSource.getNote(adapter.position)
                note.newColor()
                noteSource.update(adapter.position, note)
            }
            MENU_DELETE -> {
                val dialog: DialogFragment = DeleteDialog()
                val bundle = Bundle()
                bundle.putInt(INDEX_KEY, adapter.position)
                dialog.arguments = bundle
                dialog.show(parentFragmentManager, TAG_DELETE)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                val fragment: DialogFragment = EditNoteDialogFragment()
                fragment.show(parentFragmentManager, TAG_EDIT_NOTE)
            }
            R.id.menu_search ->
                Toast.makeText(requireContext(), R.string.search, Toast.LENGTH_SHORT).show()
            R.id.menu_about ->
                Toast.makeText(requireContext(), R.string.about, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val MENU_CHANGE_COLOR = 321
        private const val MENU_DELETE = 123
        const val INDEX_KEY = "index"
        private const val TAG_EDIT_NOTE = "TAG_EDIT_NOTE"
        private const val TAG_DELETE = "TAG_DELETE"
        fun newInstance(): ListOfNotesFragment {
            return ListOfNotesFragment()
        }
    }
}
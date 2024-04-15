package com.example.navegacion.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.actevaluabletres.R
import com.example.actevaluabletres.databinding.FragmentRegistroBinding

import com.google.firebase.auth.FirebaseAuth


class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.botonRegistro.setOnClickListener {
            val nombreUsuario = binding.editNombreUsuario.text.toString().trim() // Asume un campo para el nombre de usuario
            val correo = binding.editCorreo.text.toString().trim()
            val password = binding.editPass.text.toString().trim()

            if (nombreUsuario.isNotEmpty() && correo.isNotEmpty() && password.isNotEmpty()) {
                // Aquí iría el código para registrar al usuario en el backend
                registrarUsuario(nombreUsuario, correo, password)

                Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show()

/*                val bundle = Bundle().apply {
                    putString("correo", correo)
                    putString("password", password)
                }

                findNavController().navigate(R.id.action_registroFragment_to_loginFragment, bundle)*/
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registrarUsuario(nombreUsuario: String, correo: String, password: String) {
        // Usa Firebase Auth para registrar al usuario
        auth.createUserWithEmailAndPassword(correo, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // El registro fue exitoso
                    Log.d(TAG, "createUserWithEmail:success")


                    // Prepara un bundle para pasar el nombre de usuario al LoginFragment
                    val bundle = Bundle().apply {
                        putString("nombreUsuario", nombreUsuario)
                    }

                    // Navega de vuelta al LoginFragment, pasando el nombre de usuario como argumento
                    findNavController().navigate(R.id.action_registroFragment_to_loginFragment, bundle)
                } else {
                    // Si el registro falla, muestra un mensaje al usuario
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "El registro falló: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val TAG = "RegistroFragment"
    }
}

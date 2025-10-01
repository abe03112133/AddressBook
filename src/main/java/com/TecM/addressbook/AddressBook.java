package com.TecM.addressbook;

import java.io.*;
import java.util.*;

public class AddressBook {

    private HashMap<String, String> contacts;
    private String filename;

    // Constructor
    public AddressBook(String filename) {
        this.filename = filename;
        this.contacts = new HashMap<>();
        load();
    }

    public static void clearScreen(int c) {
        for (int i = 0; i < c; i++) {
            System.out.println();
        }
    }

    public static void pause() {
        System.out.println("\nPresione ENTER para continuar...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    // Cargar contactos desde archivo
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 2);
                if (data.length == 2) {
                    contacts.put(data[0], data[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creara uno nuevo al guardar.");
            pause();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            pause();
        }
    }

    // Guardar contactos en archivo
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Mostrar contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Crear nuevo contacto
    public void create(String phone, String name) {
        if (contacts.containsKey(phone)) {
            System.out.println("El numero ya existe en la agenda.");
        } else {
            contacts.put(phone, name);
            save();
            System.out.println("Contacto agregado.");
        }
    }

    // Eliminar contacto
    public void delete(String phone) {
        if (contacts.remove(phone) != null) {
            save();
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("El numero no existe en la agenda.");
        }
    }

    // Menú interactivo
    public void menu() {
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            clearScreen(20);
            System.out.println("\n--- Menú de Agenda ---");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            clearScreen(2);
            System.out.print("Seleccione una opcion: ");

            option = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (option) {
                case 1:
                    list();
                    pause();
                    break;
                case 2:
                    System.out.print("Ingrese numero: ");
                    String phone = sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String name = sc.nextLine();
                    create(phone, name);
                    pause();
                    break;
                case 3:
                    System.out.print("Ingrese numero a eliminar: ");
                    String delPhone = sc.nextLine();
                    delete(delPhone);
                    pause();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    pause();
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    pause();
            }
        } while (option != 4);
    }

    // Main para ejecutar el programa
    public static void main(String[] args) {
        AddressBook agenda = new AddressBook("agenda.txt");
        agenda.menu();
    }
}

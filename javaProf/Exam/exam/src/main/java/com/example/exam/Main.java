package com.example.exam;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            // Initialize DAOs
            ClientDAO clientDAO = new ClientDAO();
            CommandeDAO commandeDAO = new CommandeDAO();
            IngredientDAO ingredientDAO = new IngredientDAO();
            PlatPrincipalDAO platPrincipalDAO = new PlatPrincipalDAO();
            RepasDAO repasDAO = new RepasDAO();
            SupplementDAO supplementDAO = new SupplementDAO();

            // Step 1: Create and Save a Client
            Client client = new Client("jsj", "sad", "ddda.Bouchti@example.com", "0623456789");
            clientDAO.save(client);

            // Step 2: Create and Save Ingredients
            Ingredient viande = new Ingredient("Viande", "gramme", new BigDecimal("10"));
            Ingredient pruneaux = new Ingredient("Pruneaux", "gramme", new BigDecimal("5"));
            Ingredient poisson = new Ingredient("Poisson", "gramme", new BigDecimal("12"));
            Ingredient carotte = new Ingredient("Carotte", "gramme", new BigDecimal("3"));
            Ingredient pommeDeTerre = new Ingredient("Pomme de terre", "gramme", new BigDecimal("2"));
            Ingredient olive = new Ingredient("Olive", "gramme", new BigDecimal("1"));

            ingredientDAO.save(viande);
            ingredientDAO.save(pruneaux);
            ingredientDAO.save(poisson);
            ingredientDAO.save(carotte);
            ingredientDAO.save(pommeDeTerre);
            ingredientDAO.save(olive);

            // Step 3: Create and Save Supplements
            Supplement frites = new Supplement("Frites", new BigDecimal("11"));
            Supplement boisson = new Supplement("Boisson", new BigDecimal("12"));
            Supplement jusOrange = new Supplement("Jus d'orange", new BigDecimal("13"));
            Supplement saladeMarocaine = new Supplement("Salade marocaine", new BigDecimal("14"));

            supplementDAO.save(frites);
            supplementDAO.save(boisson);
            supplementDAO.save(jusOrange);
            supplementDAO.save(saladeMarocaine);

            // Step 4: Create and Save PlatPrincipal with Ingredients
            PlatPrincipal tajineViande = new PlatPrincipal("Tajine de viande & Pruneaux", "Delicious tajine", new BigDecimal("50"));
            tajineViande.addIngredient(viande, new BigDecimal("250"));
            tajineViande.addIngredient(pruneaux, new BigDecimal("1"));
            platPrincipalDAO.save(tajineViande);

            PlatPrincipal tajinePoulet = new PlatPrincipal("Tajine de poulet & légumes", "Healthy tajine", new BigDecimal("45"));
            tajinePoulet.addIngredient(poisson, new BigDecimal("250"));
            tajinePoulet.addIngredient(carotte, new BigDecimal("1"));
            tajinePoulet.addIngredient(pommeDeTerre, new BigDecimal("1"));
            tajinePoulet.addIngredient(olive, new BigDecimal("1"));
            platPrincipalDAO.save(tajinePoulet);

            // Step 5: Create and Save Commande
            Commande commande = new Commande(client);
            commandeDAO.save(commande); // Save the Commande first to generate its ID

            // Step 6: Create and Save Repas
            Repas repas1 = new Repas(tajineViande);
            repas1.setCommandeId(commande.getId()); // Associate Repas with the saved Commande
            repas1.addSupplement(frites);
            repas1.addSupplement(boisson);
            repasDAO.save(repas1);

            Repas repas2 = new Repas(tajinePoulet);
            repas2.setCommandeId(commande.getId()); // Associate Repas with the saved Commande
            repas2.addSupplement(jusOrange);
            repas2.addSupplement(saladeMarocaine);
            repasDAO.save(repas2);

            // Step 7: Add Repas to Commande and Update Commande
            commande.addRepas(repas1);
            commande.addRepas(repas2);
            //commandeDAO.update(commande); // Update the Commande to reflect the added Repas

            // Step 8: Retrieve Commande and Generate Ticket
            Commande retrievedCommande = commandeDAO.findById(commande.getId());
            if (retrievedCommande != null) {
                System.out.println("Bienvenue " + retrievedCommande.getClient().getNom());
                System.out.println("--------------------------------");
                System.out.println("------------TICKET-------------");
                System.out.println("--------------------------------");
                System.out.println("Nom: " + retrievedCommande.getClient().getNom());
                System.out.println("Nombre de repas: " + retrievedCommande.getRepas().size());

                int repasNumber = 1;
                for (Repas repas : retrievedCommande.getRepas()) {
                    System.out.println("Repas N°:" + repasNumber + " " + repas.getPlatPrincipal().getNom());
                    System.out.println("Ingrédients:");
                    for (var entry : repas.getPlatPrincipal().getIngredients().entrySet()) {
                        System.out.println(entry.getKey().getNom() + ": " + entry.getValue() + " " + entry.getKey().getUnite());
                    }
                    System.out.println("Suppléments:");
                    for (Supplement supplement : repas.getSupplements()) {
                        System.out.println(supplement.getNom() + ": " + supplement.getPrix());
                    }
                    System.out.println("*********");
                    repasNumber++;
                }
                System.out.println("Total: " + retrievedCommande.getTotal());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
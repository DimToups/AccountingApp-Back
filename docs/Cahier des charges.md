# Cahier des charges
## Besoin initial
- Pouvoir visualiser l'ensemble des mouvements bancaires
- Définir des types de mouvements bancaires
- Définir des budgets mensuels ou annuels pour chaque type de mouvements bancaires
- Planifier des dépenses
- Prendre en compte plusieurs comptes bancaires
## Liste des fonctionnalités et spécifications techniques
- Avoir une partie en back
- Pouvoir accéder à l'application via une version pc ou par un site.
- Stocker toutes les informations dans une base de données
- Pouvoir entrer une dépense ou une entrée d'argent :
	- Indiquer la somme
	- Indiquer le type de mouvement
	- Indiquer le libellé
	- Indiquer le compte débiteur
	- Indiquer la date (minimum le mois)
- Pouvoir visualiser ses mouvements:
	- Trier tous les mouvements selon leurs caractéristiques
	- Trier tous les mouvements selon les comptes
	- Pouvoir modifier les mouvements
- Prévoir des dépenses:
	- Définir la somme
	- Définir une description
	- Donner une date de début
	- Donner une date de fin (optionnel)
	- Donner une durée en mois (optionnel)
	- Définir le compte débiteur
	- Définir si la prévision est à prendre en compte ou non
	- Remplir automatiquement les cases optionnelles si elles ne le sont pas
- Visualiser la liste des prévisions
- Pouvoir modifier une prévision existante
- Pouvoir noter un versement:
	- Indiquer le compte débiteur
	- Indiquer le montant
	- Indiquer le compte créditeur
	- Indiquer la date
- Lister la liste des versements
- Pouvoir modifier un versement
- Lister la liste des types de dépenses
- Lister la liste des types de revenus
- Pouvoir ajouter un type de dépenses
- Pouvoir ajouter un type de revenus
- Pouvoir créer un compte (virtuel)
	- Indiquer le nom
	- Indiquer le taux (ou les taux si évolutif)
	- Indiquer le plafond
	- Indiquer une description
- Pouvoir visualiser l'ensemble des dépenses et revenus de la façon d'un calendrier avec un résultat
- Rendre l'application multi-utilisateur
- Donner un droit de regard sur ses comptes à un autre utilisateur
- Partager l'accès à un compte à un autre utilisateur
## Choix des technologies
### Back-end
**Langage**: Java Spring
**Base de données**: PostgreSQL
### Web
**Langage**: VueJS
### Client lourd
**Langage**: À décider
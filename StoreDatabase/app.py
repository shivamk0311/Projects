
# Import the necessary modules from Flask and MySQL connector
from flask import Flask, flash, render_template, request, redirect, url_for
import mysql.connector

# Initialize the Flask application
app = Flask(__name__)


db = mysql.connector.connect(
    host="localhost",       
    user="root",            
    password="",          
    database="store_database" 
)
# Define the home route to display records from the database
@app.route('/')
def index():
    # Create a cursor to interact with the database
    cursor = db.cursor(dictionary=True)  
    
    # Execute a query to fetch all records from the specified table
    cursor.execute("SELECT * FROM item ")  
    
    # Fetch all the results from the executed query
    results = cursor.fetchall()
    
    # Close the cursor after retrieving data
    cursor.close()
    
    # Render the 'index.html' template, passing the results to be displayed in the HTML
    return render_template('index.html', results=results)

#Route to handle form submission to search for an item based on item id or item number 
@app.route('/search', methods=['POST'])
def search():
    # Retrieve form data submitted by the user
    item_name = request.form['Iname']  
    item_id = request.form['iId']  
    
    # Create a cursor to execute the insert query
    cursor = db.cursor(dictionary=True)
    if item_name and item_id:
        cursor.execute("SELECT * FROM item WHERE iId = %s AND Iname = %s", (item_id, item_name))
    elif item_name:
        cursor.execute("SELECT * FROM item WHERE Iname = %s", (item_name,))
    elif item_id:
        cursor.execute("SELECT * FROM item WHERE iId = %s", (item_id,))
    else:
        return "Please enter an Item Id or an Item name.", 
        
    results = cursor.fetchall()
    
    # Close the cursor after inserting data
    cursor.close()
    
    # Render the page again 
    return render_template('index.html', results=results)

# Route to handle form submission to add a new record to the database
@app.route('/add', methods=['POST'])
def add():
    # Retrieve form data submitted by the user
    item_id = int(request.form['iId'])  
    item_name = request.form['Iname']  
    item_price = float(request.form['Sprice']) 
    item_description = request.form['Idescription']  
    
    # Create a cursor to execute the insert query
    cursor = db.cursor()
    
    # Insert the new record into the specified table
    cursor.execute("INSERT INTO item (iId, Iname, Sprice, Idescription) VALUES (%s, %s, %s, %s)", (item_id, item_name, item_price, item_description ))
    
    # Commit the transaction to save changes to the database
    db.commit()
    
    # Close the cursor after inserting data
    cursor.close()
    
    
    # Redirect the user back to the home page after submission
    return redirect(url_for('index'))

# Route to handle form submission to update a record from the database
@app.route('/update', methods=['POST'])
def update():
    # Retrieve form data submitted by the user
    currentName = request.form.get('currentName')
    NewName = request.form.get('NewName')
    
    # Create a cursor to execute the insert query
    cursor = db.cursor()
    
    # Insert the new record into the specified table
    cursor.execute("UPDATE item SET Iname = %s WHERE Iname = %s", (NewName, currentName))
          
    # Commit the transaction to save changes to the database
    db.commit()
    
    # Close the cursor after inserting data
    cursor.close()
    
   
    # Redirect the user back to the home page after submission
    return redirect(url_for('index'))

# Route to handle form submission to delete a record from the database
@app.route('/delete', methods=['POST'])
def delete():
    # Retrieve form data submitted by the user
    item_name = request.form['Iname']
    
    # Create a cursor to execute the insert query
    cursor = db.cursor()
    
    # Insert the new record into the specified table
    cursor.execute("DELETE FROM item WHERE Iname = %s", (item_name,))
          
    # Commit the transaction to save changes to the database
    db.commit()
    
    # Close the cursor after inserting data
    cursor.close()
    
   
    # Redirect the user back to the home page after submission
    return redirect(url_for('index'))


# Run the Flask application
if __name__ == '__main__':
    # Setting debug=True for development; it auto-reloads when you make changes
    app.run(debug=True)

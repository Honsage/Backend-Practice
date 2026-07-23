require('dotenv').config();
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { MongoClient, ObjectId } = require('mongodb');

const app = express();
const PORT = process.env.PORT || 8080;
const MONGODB_URI = process.env.MONGODB_URI;

app.use(cors());
app.use(bodyParser.json());

// db connection
let db;

MongoClient.connect(MONGODB_URI)
    .then(client => {
        db = client.db();

        app.listen(PORT);
    })
    .catch(_ => process.exit(1));

const handleError = (res, msg, code = 500) => {
    res.status(code).json({ error: msg });
};

const getCollection = () => {
    if (!db) throw new Error("DB has not initialized");
    return db.collection('contacts');
};


app.get('/api/contacts', async (req, res) => {
    try {
        const collection = getCollection();
        const contacts = await collection.find({}).toArray();
        res.json(contacts);
    } catch (err) {
        handleError(res, err);
    }
});

app.post('/api/contacts', async (req, res) => {
  try {
    const newContact = req.body;

    if (!newContact.name) {
      return res.status(400).json({ error: '"name" is necessary field' });
    }

    newContact.createdAt = new Date();

    const collection = getCollection();
    const result = await collection.insertOne(newContact);

    res.status(201).json({ ...newContact, _id: result.insertedId });
  } catch (err) {
    handleError(res, err, 400);
  }
});

app.get('/api/contacts/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const collection = getCollection();

    if (!ObjectId.isValid(id)) {
      return res.status(400).json({ error: 'Invalid ID format' });
    }

    const contact = await collection.findOne({ _id: new ObjectId(id) });

    if (!contact) {
      return res.status(404).json({ error: 'Contact is not found' });
    }

    res.json(contact);
  } catch (err) {
    handleError(res, err);
  }
});

app.put('/api/contacts/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const updatedData = req.body;

    if (!ObjectId.isValid(id)) {
      return res.status(400).json({ error: 'Invalid ID format' });
    }

    delete updatedData._id;

    const collection = getCollection();
    const result = await collection.updateOne(
      { _id: new ObjectId(id) },
      { $set: updatedData }
    );

    if (result.matchedCount === 0) {
      return res.status(404).json({ error: 'Contact is not found' });
    }

    const updatedContact = await collection.findOne({ _id: new ObjectId(id) });
    res.json(updatedContact);
  } catch (err) {
    handleError(res, err);
  }
});

app.delete('/api/contacts/:id', async (req, res) => {
  try {
    const id = req.params.id;

    if (!ObjectId.isValid(id)) {
      return res.status(400).json({ error: 'Invalid ID format' });
    }

    const collection = getCollection();
    const result = await collection.deleteOne({ _id: new ObjectId(id) });

    if (result.deletedCount === 0) {
      return res.status(404).json({ error: 'Contact is not found' });
    }

    res.status(204).send();
  } catch (err) {
    handleError(res, err);
  }
});

app.use((req, res) => {
  res.status(404).json({ error: `Route ${req.originalUrl} is not found` });
});

app.use((err, req, res, next) => {
  res.status(500).json({ error: 'Internal server error' });
});
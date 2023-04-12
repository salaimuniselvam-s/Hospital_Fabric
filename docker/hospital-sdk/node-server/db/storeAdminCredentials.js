const express = require("express");
const UserDetails = require("./schema");
const { hashPassword } = require("../utils/hashPassword");
const router = express.Router();

router.post("/", (req, res) => {
  // Storing Admin Credentials into the Mongodb Database
  const adminDetail = new UserDetails({
    username: req.body.username,
    password: hashPassword(req.body.password),
    role: req.body.role,
    hospitalId: req.body.hospitalId,
  });

  adminDetail
    .save()
    .then(() =>
      res.status(200).send("Admin Credentials are Updated successfully")
    )
    .catch((error) => {
      console.error(error);
      return res.status(400).send("Updating Records Failed.Please Try Again");
    });
});

module.exports = router;

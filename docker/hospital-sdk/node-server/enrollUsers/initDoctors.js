const { TEMP_PASSWORD } = require("../utils/utils");

const DOCTOR_DETAILS = [
  {
    hospitalId: "1",
    firstName: "Rajesh",
    lastName: "Kumar",
    speciality: "Neurosurgery",
    username: "Rajesh Kumar",
    password: TEMP_PASSWORD,
  },
  {
    hospitalId: "2",
    firstName: "Kailash",
    lastName: "Balaji",
    speciality: "Cardiology",
    username: "Kailash Balaji",
    password: TEMP_PASSWORD,
  },
  {
    hospitalId: "1",
    firstName: "Deepak",
    lastName: "Chakravarthy",
    speciality: "Cardiology",
    username: "Deepak Chakravarthy",
    password: TEMP_PASSWORD,
  },
  {
    hospitalId: "2",
    firstName: "Mahesh",
    lastName: "Babu",
    speciality: "Neurosurgery",
    username: "Mahesh Babu",
    password: TEMP_PASSWORD,
  },
];

module.exports = DOCTOR_DETAILS;

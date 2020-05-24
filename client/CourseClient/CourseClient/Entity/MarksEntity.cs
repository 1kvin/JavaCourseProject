using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.Entity
{
    public class MarksEntity : IEntity
    {
        public int id { get; set; }
        public PeopleEntity studentId { get; set; }
        public SubjectsEntity subjectId { get; set; }
        public PeopleEntity teacherId { get; set; }
        public int value { get; set; }
        public string GetViewInTable()
        {
            return "mark";
        }
    }
}

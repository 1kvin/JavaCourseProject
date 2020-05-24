using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.Entity
{
    public interface IEntity
    {
        string GetViewInTable();
        int id { get; set; }
    }
}

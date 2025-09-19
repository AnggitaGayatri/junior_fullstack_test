<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Student extends Model
{
    use HasFactory;

    protected $fillable = [
        'student_number',
        'name',
        'class',
        'address',
    ];

    public function schedules()
    {
    return $this->hasMany(Schedule::class);
    }
}

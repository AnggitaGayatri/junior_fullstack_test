<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    public function up(): void
    {
        if (!Schema::hasColumn('teachers', 'subject_id')) {
            Schema::table('teachers', function (Blueprint $table) {
                $table->unsignedBigInteger('subject_id')->nullable()->after('full_name');
            });
        }
    }

    public function down(): void
    {
        if (Schema::hasColumn('teachers', 'subject_id')) {
            Schema::table('teachers', function (Blueprint $table) {
                $table->dropColumn('subject_id');
            });
        }
    }
};


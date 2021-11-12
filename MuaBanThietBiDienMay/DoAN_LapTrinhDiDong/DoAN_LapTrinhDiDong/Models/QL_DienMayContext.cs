using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace DoAN_LapTrinhDiDong.Models
{
    public partial class QL_DienMayContext : DbContext
    {
        public QL_DienMayContext()
        {
        }

        public QL_DienMayContext(DbContextOptions<QL_DienMayContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Chitiethd> Chitiethds { get; set; }
        public virtual DbSet<HoaDon> HoaDons { get; set; }
        public virtual DbSet<LoaiSanPham> LoaiSanPhams { get; set; }
        public virtual DbSet<SanPham> SanPhams { get; set; }
        public virtual DbSet<TaiKhoan> TaiKhoans { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                optionsBuilder.UseSqlServer("Server=DESKTOP-PTPAKPN;Database=QL_DienMay;Trusted_Connection=True;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasAnnotation("Relational:Collation", "SQL_Latin1_General_CP1_CI_AS");

            modelBuilder.Entity<Chitiethd>(entity =>
            {
                entity.HasKey(e => e.MaCthd)
                    .HasName("PK_ChiTietHoaDon");

                entity.ToTable("CHITIETHD");

                entity.Property(e => e.MaCthd).HasColumnName("MaCTHD");

                entity.Property(e => e.MaHd).HasColumnName("MaHD");

                entity.Property(e => e.MaSp).HasColumnName("MaSP");

                entity.HasOne(d => d.MaHdNavigation)
                    .WithMany(p => p.Chitiethds)
                    .HasForeignKey(d => d.MaHd)
                    .HasConstraintName("FK_ChiTietHoaDon_HoaDon");

                entity.HasOne(d => d.MaSpNavigation)
                    .WithMany(p => p.Chitiethds)
                    .HasForeignKey(d => d.MaSp)
                    .HasConstraintName("FK_CHITIETHD_SanPham");
            });

            modelBuilder.Entity<HoaDon>(entity =>
            {
                entity.HasKey(e => e.MaHd);

                entity.ToTable("HoaDon");

                entity.Property(e => e.MaHd).HasColumnName("MaHD");

                entity.Property(e => e.DiaChi).HasColumnType("text");

                entity.Property(e => e.MaTk).HasColumnName("MaTK");

                entity.Property(e => e.NgayLap).HasColumnType("date");

                entity.Property(e => e.TongTien).HasDefaultValueSql("((0))");

                entity.HasOne(d => d.MaTkNavigation)
                    .WithMany(p => p.HoaDons)
                    .HasForeignKey(d => d.MaTk)
                    .HasConstraintName("FK_HoaDon_TaiKhoan");
            });

            modelBuilder.Entity<LoaiSanPham>(entity =>
            {
                entity.HasKey(e => e.MaLoai);

                entity.ToTable("LoaiSanPham");

                entity.Property(e => e.MaLoai)
                    .HasMaxLength(2)
                    .IsUnicode(false)
                    .IsFixedLength(true);

                entity.Property(e => e.Image).HasColumnType("text");

                entity.Property(e => e.TenLoai).HasColumnType("ntext");
            });

            modelBuilder.Entity<SanPham>(entity =>
            {
                entity.HasKey(e => e.MaSanPham);

                entity.ToTable("SanPham");

                entity.HasIndex(e => e.SoLuongTon, "IX_SanPham");

                entity.Property(e => e.Image).HasColumnType("ntext");

                entity.Property(e => e.MaLoai)
                    .HasMaxLength(2)
                    .IsUnicode(false)
                    .IsFixedLength(true);

                entity.Property(e => e.MoTa).HasColumnType("ntext");

                entity.Property(e => e.TenSanPham).HasColumnType("ntext");

                entity.HasOne(d => d.MaLoaiNavigation)
                    .WithMany(p => p.SanPhams)
                    .HasForeignKey(d => d.MaLoai)
                    .HasConstraintName("FK_SanPham_LoaiSanPham");
            });

            modelBuilder.Entity<TaiKhoan>(entity =>
            {
                entity.HasKey(e => e.MaTk);

                entity.ToTable("TaiKhoan");

                entity.Property(e => e.MaTk).HasColumnName("MaTK");

                entity.Property(e => e.DiaChi).HasColumnType("ntext");

                entity.Property(e => e.Email).HasColumnType("text");

                entity.Property(e => e.GioiTinh).HasColumnType("text");

                entity.Property(e => e.Password).HasColumnType("text");

                entity.Property(e => e.Username).HasColumnType("text");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
